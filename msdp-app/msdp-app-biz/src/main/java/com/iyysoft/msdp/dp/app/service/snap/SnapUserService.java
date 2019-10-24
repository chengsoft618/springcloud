package com.iyysoft.msdp.dp.app.service.snap;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.dp.app.entity.snap.SnapUser;

/**
 * 随手拍-用户
 *
 * @author iyysoft code generator
 * @date 2019-08-21 13:18:42
 */
public interface SnapUserService extends IService<SnapUser> {

    /**
     * 判断用户是否被封号和禁言
     * @param userId
     * @return
     */
    R validateSnapUser(String userId);

    /**
     * 判断用户是否被封号
     * @param userId
     * @return
     */
    R validateSnapUser0(String userId);

    R getSnapUser(String userId);




}
