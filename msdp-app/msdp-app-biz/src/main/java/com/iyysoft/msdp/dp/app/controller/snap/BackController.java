package com.iyysoft.msdp.dp.app.controller.snap;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.data.mybatis.QPage;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.dp.app.dto.snap.ShotBackListDto;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShotTag;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotCheckEnum;
import com.iyysoft.msdp.dp.app.enums.snap.ShotStatusEnum;
import com.iyysoft.msdp.dp.app.entity.snap.SnapLikeRecord;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.service.snap.SnapAsycnService;
import com.iyysoft.msdp.dp.app.service.snap.SnapCommentService;
import com.iyysoft.msdp.dp.app.service.snap.SnapLikeRecordService;
import com.iyysoft.msdp.dp.app.service.snap.SnapMediaService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotTagService;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import com.iyysoft.msdp.dp.app.vo.snap.BackCommListVo;
import com.iyysoft.msdp.dp.app.vo.snap.BackLikeCountVo;
import com.iyysoft.msdp.dp.app.vo.snap.BackLikeListVo;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotBackDetailVo;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotBackListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 码农
 * @Date: 2019/8/27 15:06
 */
@Api(tags = "随手拍-随手拍后台管理", value = "")
@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/shotback")
public class BackController {

    private final SnapShotService snapShotService;
    private final SnapCommentService snapCommentService;
    private final SnapMediaService snapMediaService;
    private final SnapShotTagService snapShotTagService;
    private final SnapLikeRecordService snapLikeRecordService;
    private final SnapAsycnService snapAsycnService;
    private final SnapUserService snapUserService;

    @ApiOperation("后台管理-随手拍列表")
    @ApiResponse(code = 0, message = "", response = SnapShotBackListVo.class)
    @GetMapping
    public R list(@ApiParam("查询对象") ShotBackListDto dto, @ApiParam("分页对象") QPage page) {
        IPage<SnapShot> iPage = snapShotService.selectBackShotList(dto, page);
        IPage<SnapShotBackListVo> voIPage = iPage.convert(SnapShotBackListVo::createVo);
        return new R<>(voIPage);
    }

    @ApiOperation("随手拍详情")
    @GetMapping("/{shotSid}")
    public R detail(@PathVariable String shotSid) {
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(RAppREnum.CODE.FAILED);
        }
        SnapUser snapUser = (SnapUser) snapUserService.getSnapUser(snapShot.getCreateId()).getData();
        //标签列表
        List<SnapShotTag> snapShotTagList = snapShotTagService.list(
                Wrappers.<SnapShotTag>lambdaQuery()
                        .eq(SnapShotTag::getShotSid, shotSid)
        );
        //媒体资源
        List<SnapMedia> snapMediaList = snapMediaService.list(
                Wrappers.<SnapMedia>lambdaQuery()
                        .eq(SnapMedia::getShotSid, shotSid)
        );
        return new R<>(SnapShotBackDetailVo.createVo(snapShot, snapUser, snapShotTagList, snapMediaList));
    }

    @ApiOperation("随手拍详情-评论列表")
    @GetMapping("/comm/{shotSid}")
    public R commList(@PathVariable String shotSid, @ApiParam("分页对象") QPage qPage) {
        Page page = new Page<>(qPage.getPageNum(), qPage.getPageSize());
        QueryWrapper<SnapComment> snapCommentWrapper = new QueryWrapper<>();
        snapCommentWrapper.eq("sc.shot_sid", shotSid);
        IPage<SnapComment> iPage = snapCommentService.selectBackCommList(page, snapCommentWrapper);
        IPage<BackCommListVo> voPage = iPage.convert(BackCommListVo::createVo);
        return new R<>(voPage);
    }

    @ApiOperation("随手拍详情-点赞列表")
    @GetMapping("/like/{shotSid}")
    public R likeList(@PathVariable String shotSid, @ApiParam("分页对象") QPage qPage) {
        IPage<SnapLikeRecord> snapLikeRecordIPage = new Page<>(qPage.getPageNum(), qPage.getPageSize());
        snapLikeRecordService.page(snapLikeRecordIPage,
                Wrappers.<SnapLikeRecord>lambdaQuery()
                        .eq(SnapLikeRecord::getShotSid, shotSid)
        );
        IPage<BackLikeListVo> voPage = snapLikeRecordIPage.convert(BackLikeListVo::createVo);
        return new R<>(voPage);
    }

    @SysLog("删除随手拍")
    @ApiOperation("删除随手拍")
    @DeleteMapping("/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R del(@PathVariable String shotSid) {
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(REnum.CODE.FAILED);
        }
        if (snapShotService.removeById(shotSid)) {
            snapAsycnService.delShot(shotSid);
        }
        return new R<>();
    }

    @SysLog("隐藏/显示,随手拍")
    @ApiOperation("隐藏/显示,随手拍")
    @PutMapping("/play/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R play(@PathVariable String shotSid) {
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(REnum.CODE.FAILED);
        }
        if (snapShot.getStatus() == ShotStatusEnum.HIDE) {
            snapShotService.update(
                    Wrappers.<SnapShot>lambdaUpdate()
                            .set(SnapShot::getStatus, ShotStatusEnum.SHOW)
                            .eq(SnapShot::getSid, shotSid));
        } else {
            snapShotService.update(
                    Wrappers.<SnapShot>lambdaUpdate()
                            .set(SnapShot::getStatus, ShotStatusEnum.HIDE)
                            .eq(SnapShot::getSid, shotSid));
        }
        return new R<>();
    }

    @SysLog("审核随手拍")
    @ApiOperation("审核随手拍")
    @PutMapping("/check/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R check(@PathVariable String shotSid,
                   @ApiParam("通过-PASS,未通过-FAIL") @NotNull @RequestParam ShotCheckEnum checkEnum) {
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null || snapShot.getCheck() == ShotCheckEnum.PASS || snapShot.getCheck() == ShotCheckEnum.FAIL) {
            return new R(REnum.CODE.FAILED);
        }
        if( checkEnum == ShotCheckEnum.PASS ){
            snapShotService.update(
                    Wrappers.<SnapShot>lambdaUpdate()
                            .set(SnapShot::getCheck, checkEnum)
                            .set(SnapShot::getStatus, ShotStatusEnum.SHOW)
                            .eq(SnapShot::getSid, shotSid));
        }else if( checkEnum == ShotCheckEnum.FAIL ) {
            snapShotService.update(
                    Wrappers.<SnapShot>lambdaUpdate()
                            .set(SnapShot::getCheck, checkEnum)
                            .set(SnapShot::getStatus, ShotStatusEnum.HIDE)
                            .eq(SnapShot::getSid, shotSid));
        }
        return new R<>();
    }

    @SysLog("删除随手拍的评论")
    @ApiOperation("删除随手拍的评论")
    @DeleteMapping("comm/{commSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_del') or hasRole('msdp')")
    public R commDel(@PathVariable String commSid) {
        return snapShotService.notComm(commSid);
    }

    @ApiOperation("获取点赞数量")
    @GetMapping("/likeNum/{shotSid}")
    public R likeNum(@PathVariable String shotSid){
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(REnum.CODE.FAILED);
        }
        return new R<>(BackLikeCountVo.createVo(snapShot));
    }
    @SysLog("增加点赞量")
    @ApiOperation("增加点赞量")
    @PutMapping("/like/{shotSid}")
    //@PreAuthorize("@pms.hasPermission('generator_snapshot_add') or hasRole('msdp'))")
    public R likeAdd(@PathVariable String shotSid,
                     @ApiParam("增加的点赞数量")@RequestParam Integer likenum) {
        return snapShotService.likef(shotSid, likenum);
    }


}
