package com.iyysoft.msdp.dp.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.common.core.constant.enums.REnum;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.dp.app.entity.snap.SnapComment;
import com.iyysoft.msdp.dp.app.entity.snap.SnapShot;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.vo.snap.SnapShotShareVo;
import com.iyysoft.msdp.dp.app.entity.snap.SnapMedia;
import com.iyysoft.msdp.dp.app.service.snap.SnapCommentService;
import com.iyysoft.msdp.dp.app.service.snap.SnapMediaService;
import com.iyysoft.msdp.dp.app.service.snap.SnapShotService;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
import java.util.List;


/**
 * @Author: 码农
 * @Date: 2019/8/26 16:14
 */
@Api(tags = "对外开放类", value = "")
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/open")
public class OpenController {

    private final SnapShotService snapShotService;
    private final SnapMediaService snapMediaService;
    private final SnapCommentService snapCommentService;
    private final SnapUserService snapUserService;
    private final SnapRdManager snapRdManager;

    /**
     * 通过id查询随手拍
     *
     * @param shotSid id
     * @return R
     */
    @ApiOperation("获取随手拍详情")
    @GetMapping("/shotshare/{shotSid}")
    public R shotshare(@PathVariable String shotSid) {
        String jsonStr = snapRdManager.shareShotGet(shotSid);
        if(jsonStr != null){
            return new R<>(JSON.parseObject(jsonStr,SnapShotShareVo.class));
        }
        SnapShot snapShot = snapShotService.getById(shotSid);
        if (snapShot == null) {
            return new R(REnum.CODE.FAILED);
        }
        SnapUser snapUser = snapUserService.getOne(
                Wrappers.<SnapUser>lambdaQuery()
                        .select(SnapUser::getHead)
                        .eq(SnapUser::getUserId, snapShot.getCreateId())
        );
        QueryWrapper<SnapComment> snapCommentWrapper = new QueryWrapper<>();
        snapCommentWrapper.eq("sc.shot_sid", shotSid);
        snapCommentWrapper.orderByDesc("sc.create_date");
        List<SnapComment> snapCommentList = snapCommentService.selectOpenCommList(snapCommentWrapper);
        List<SnapMedia> snapMediaList = snapMediaService.list(
                Wrappers.<SnapMedia>lambdaQuery()
                        .eq(SnapMedia::getShotSid, shotSid)
        );
        SnapShotShareVo vo = SnapShotShareVo.createVo(snapShot, snapUser, snapCommentList, snapMediaList);
        snapRdManager.shareShotSet(shotSid,JSONObject.toJSONString(vo));
        return new R<>(vo);
    }

    @ApiOperation(".net更换头像")
    @PutMapping("/head")
    public void changeHead(@RequestParam String userId, @RequestParam String head) {
        snapUserService.update(
                Wrappers.<SnapUser>lambdaUpdate()
                        .set(SnapUser::getHead, head)
                        .eq(SnapUser::getUserId, userId)
        );
    }


}
