package com.iyysoft.msdp.dp.app.controller.snap;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.common.security.service.MsdpUser;
import com.iyysoft.msdp.common.security.util.SecurityUtils;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;
import com.iyysoft.msdp.dp.app.vo.snap.SnapUserVo;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import com.iyysoft.msdp.dp.sys.feign.RemoteUserService;
import com.iyysoft.msdp.dp.sys.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * 随手拍-用户
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:42
 */
@Api(tags = "随手拍-用户", value = "")
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/app/v1/snapuser")
public class SnapUserController {

    private final SnapUserService snapUserService;
    private final RemoteUserService remoteUserService;


    /**
     * 用户首次进入随手拍，需创建随手拍账户，取一个昵称
     *
     * @param
     * @return R
     */
    @ApiOperation(value = "创建随手拍账户")
    @SysLog("创建随手拍账户")
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('generator_snapuser_add') or hasRole('msdp'))")
    public R post(@RequestParam @NotNull @Length(max = 20) String nickName) {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R res = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (res.getCode() == RAppREnum.CODE.USER_NOT_REGISTER.getCode()) {
            int count1 = snapUserService.count(Wrappers.<SnapUser>lambdaQuery().eq(SnapUser::getNickName, nickName));
            if (count1 > 0) {
                return new R<>(RAppREnum.NICKNAME_REPEAT);
            }
            R r = remoteUserService.userInfoByUserId(msdpUser.getUserId());
            if (!r.getResult()) {
                return r;
            }
            UserInfoVo userInfoVo = (UserInfoVo) r.getData();
            SnapUser record = new SnapUser();
            record.setNickName(nickName);
            record.setStatus(UserStatusEnum.NORMAL);
            record.setUserId(msdpUser.getUserId());
            record.setCreateId(msdpUser.getUserId());
            record.setUpdateId(msdpUser.getUserId());
            record.setHead(userInfoVo.getAvatar());
            record.setPhone(userInfoVo.getPhone());
            record.setRealName(userInfoVo.getUserName());
            record.setRealPhoto(userInfoVo.getRealPhoto());
            snapUserService.save(record);
            return new R<>(SnapUserVo.createVo(record));
        }
        if (res.getResult()) {
            return new R<>(SnapUserVo.createVo((SnapUser) res.getData()));
        }
        return res;
    }

    @ApiOperation(value = "获取当前随手拍账户")
    @GetMapping
    public R get() {
        MsdpUser msdpUser = SecurityUtils.getUser();
        R res = snapUserService.validateSnapUser(msdpUser.getUserId());
        if (res.getResult()) {
            return new R<>(SnapUserVo.createVo((SnapUser) res.getData()));
        }
        return res;
    }


}
