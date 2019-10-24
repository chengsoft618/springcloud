package com.iyysoft.msdp.dp.app.controller.snap;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.common.security.service.MsdpUser;
import com.iyysoft.msdp.common.security.util.SecurityUtils;
import com.iyysoft.msdp.dp.app.dto.snap.ShotAddDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.ReportTypeEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotTagEnum;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotDetailCommentVo;
import com.iyysoft.msdp.dp.app.entity.snap.SnapReport;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.service.snap.SnapAsycnService;
import com.iyysoft.msdp.dp.app.service.snap.SnapCommentService;
import com.iyysoft.msdp.dp.app.service.snap.SnapMediaService;
import com.iyysoft.msdp.dp.app.service.snap.SnapReportService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 随手拍-发布的随手拍
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:48
 */
@Api(tags = "随手拍-随手拍", value = "")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/snapshot")
public class ShotController {

    private final SnapShotService snapShotService;
    private final SnapUserService snapUserService;
    private final SnapReportService snapReportService;
    private final SnapCommentService snapCommentService;
    private final SnapMediaService snapMediaService;
    private final SnapAsycnService snapAsycnService;

    private final SnapRdManager rdManager;

    /**
     * 发布随手拍
     *
     * @param
     * @return
     */
    @SysLog("发布随手拍")
    @ApiOperation("发布随手拍")
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R publishShot(@Validated @RequestBody ShotAddDto shotAddDto) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        SnapUser snapUser = (SnapUser) r.getData();
        return snapShotService.publishShot(shotAddDto, msdpUser.getUserId(), snapUser.getNickName());
    }

    /**
     * 点赞/取消点赞
     *
     * @param
     * @return
     */
    @SysLog("点赞/取消点赞随手拍")
    @ApiOperation("点赞/取消点赞随手拍")
    @PutMapping("/like/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R like(@PathVariable String shotSid) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        SnapUser snapUser = (SnapUser)r.getData();
        return snapShotService.like(shotSid, msdpUser.getUserId(), snapUser.getNickName());
    }



    /**
     * 评论
     *
     * @param
     * @return
     */
    @SysLog("评论随手拍")
    @ApiOperation("评论随手拍")
    @PutMapping("/comment/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R comment(@PathVariable String shotSid, @NotBlank @RequestParam String comment) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        SnapShot snapShot = snapShotService.getOne(
                Wrappers.<SnapShot>lambdaQuery()
                        .select(SnapShot::getSid, SnapShot::getCommnum, SnapShot::getStatus, SnapShot::getVersion)
                        .eq(SnapShot::getSid, shotSid)
        );
        if (snapShot == null || snapShot.getStatus() == ShotStatusEnum.HIDE) {
            return new R(RAppREnum.SHOT_DELETED);
        }
        SnapUser snapUser = (SnapUser) r.getData();
        return snapShotService.comm(snapShot, snapUser, comment);

    }

    /**
     * 举报
     *
     * @param
     * @return
     */
    @SysLog("举报随手拍")
    @ApiOperation("举报随手拍")
    @PutMapping("/report/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R report(@NotBlank @PathVariable String shotSid,
                    @NotNull @RequestParam ReportTypeEnum reportType) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        //举报人
        SnapUser repUser = (SnapUser) r.getData();
        SnapShot snapShot = snapShotService.getOne(
                Wrappers.<SnapShot>lambdaQuery()
                        .select(SnapShot::getSid, SnapShot::getStatus, SnapShot::getCreateId , SnapShot::getTag)
                        .eq(SnapShot::getSid, shotSid)
        );
        if (snapShot == null || snapShot.getStatus() == ShotStatusEnum.HIDE) {
            return new R(RAppREnum.SHOT_DELETED);
        }
        //被举报人
        SnapUser pubUser = (SnapUser)snapUserService.getSnapUser(snapShot.getCreateId()).getData();
        return snapShotService.report(snapShot, reportType,repUser, pubUser);

    }

    /**
     * 通过id删除随手拍-发布的随手拍
     *
     * @param shotSid id
     * @return R
     */
    @SysLog("删除随手拍")
    @ApiOperation("删除自己发的随手拍")
    @DeleteMapping("/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R delete(@PathVariable String shotSid) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.getSnapUser(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null || snapShot.getStatus() == ShotStatusEnum.HIDE) {
            return new R(RAppREnum.SHOT_DELETED);
        }
        if (msdpUser.getUserId().equals(snapShot.getCreateId())) {
            if(snapShotService.removeById(shotSid)){
                snapAsycnService.delShot(shotSid);
                return new R();
            }
        }
        return new R(REnum.CODE.FAILED);
    }


    @ApiOperation("随手拍列表")
    @GetMapping()
    public R list(@RequestParam(defaultValue = "false") Boolean onlyme,
                  @RequestParam(required = false) ShotTagEnum shotTag,
                  @ApiParam("分页对象") QPage page) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser0(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        IPage iPage = snapShotService.selectAppShotList(onlyme, shotTag, page, msdpUser.getUserId());
        return new R<>(iPage);
    }

    @ApiOperation("随手拍详情-评论列表")
    @GetMapping("/comment/{shotSid}")
    public R commentList(@PathVariable String shotSid, @ApiParam("分页对象") QPage qPage) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R r = snapUserService.validateSnapUser0(msdpUser.getUserId());
        if (r.getCode() != 0) {
            return r;
        }
        Page page = new Page<>(qPage.getPageNum(), qPage.getPageSize());
        QueryWrapper<SnapComment> snapCommentWrapper = new QueryWrapper<>();
        snapCommentWrapper.eq("sc.shot_sid", shotSid);
        snapCommentWrapper.orderByDesc("sc.create_date");
        IPage<SnapComment> iPage = snapCommentService.selectBackCommList(page, snapCommentWrapper);
        IPage<SnapShotDetailCommentVo> voPage = iPage.convert(SnapShotDetailCommentVo::createVo);
        return new R<>(voPage);
    }


}
