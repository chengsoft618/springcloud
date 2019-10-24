package com.iyysoft.msdp.dp.app.service.snap.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;
import com.iyysoft.msdp.dp.app.enums.RAppREnum;
import com.iyysoft.msdp.dp.app.enums.snap.UserStatusEnum;
import com.iyysoft.msdp.dp.app.manager.SnapRdManager;
import com.iyysoft.msdp.dp.app.mapper.SnapUserMapper;
import com.iyysoft.msdp.dp.app.service.snap.SnapUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 随手拍-用户
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:42
 */
@Service
@AllArgsConstructor
public class SnapUserServiceImpl extends ServiceImpl<SnapUserMapper, SnapUser> implements SnapUserService {

    private final SnapRdManager rdManager;

    @Override
    public R validateSnapUser(String userId) {
        SnapUser snapUser = rdManager.snapUserGet(userId);
        if (snapUser == null) {
            snapUser = this.getOne(
                    Wrappers.<SnapUser>lambdaQuery().eq(SnapUser::getUserId, userId)
            );
            rdManager.snapUserSet(snapUser);
        }

        if (snapUser == null) {
            return new R(RAppREnum.CODE.USER_NOT_REGISTER);
        }
        //判断是否封号
        if (snapUser.getStatus() == UserStatusEnum.DISABLE) {
            LocalDateTime freeTime = snapUser.getFreeTime();
            if (freeTime.isAfter(LocalDateTime.now())) {
                return new R(RAppREnum.DISABLE);
            } else {
                this.update(Wrappers.<SnapUser>lambdaUpdate()
                        .set(SnapUser::getStatus, UserStatusEnum.NORMAL)
                        .eq(SnapUser::getSid, snapUser.getSid())
                );
                snapUser.setStatus(UserStatusEnum.NORMAL);
                rdManager.snapUserSet(snapUser);
            }
        }
        //判断是否禁言
        if (snapUser.getStatus() == UserStatusEnum.BAN) {
            LocalDateTime freeTime = snapUser.getFreeTime();
            if (freeTime.isAfter(LocalDateTime.now())) {
                return new R(RAppREnum.BANNED);
            } else {
                this.update(Wrappers.<SnapUser>lambdaUpdate()
                        .set(SnapUser::getStatus, UserStatusEnum.NORMAL)
                        .eq(SnapUser::getSid, snapUser.getSid())
                );
                snapUser.setStatus(UserStatusEnum.NORMAL);
                rdManager.snapUserSet(snapUser);
            }
        }
        return new R<>(snapUser);
    }

    @Override
    public R validateSnapUser0(String userId) {
        SnapUser snapUser = rdManager.snapUserGet(userId);
        if (snapUser == null) {
            snapUser = this.getOne(
                    Wrappers.<SnapUser>lambdaQuery().eq(SnapUser::getUserId, userId)
            );
            rdManager.snapUserSet(snapUser);
        }

        if (snapUser == null) {
            return new R(RAppREnum.CODE.USER_NOT_REGISTER);
        }
        //判断是否封号
        if (snapUser.getStatus() == UserStatusEnum.DISABLE) {
            LocalDateTime freeTime = snapUser.getFreeTime();
            if (freeTime.isAfter(LocalDateTime.now())) {
                return new R(RAppREnum.DISABLE);
            } else {
                this.update(Wrappers.<SnapUser>lambdaUpdate()
                        .set(SnapUser::getStatus, UserStatusEnum.NORMAL)
                        .eq(SnapUser::getSid, snapUser.getSid())
                );
                snapUser.setStatus(UserStatusEnum.NORMAL);
                rdManager.snapUserSet(snapUser);
            }
        }
        return new R<>(snapUser);
    }

    @Override
    public R getSnapUser(String userId) {
        SnapUser snapUser = rdManager.snapUserGet(userId);
        if (snapUser == null) {
            snapUser = this.getOne(
                    Wrappers.<SnapUser>lambdaQuery().eq(SnapUser::getUserId, userId)
            );
            rdManager.snapUserSet(snapUser);
        }
        return new R<>(snapUser);
    }


}
