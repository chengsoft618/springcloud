package com.iyysoft.msdp.dp.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.sys.dto.UserInfo;
import com.iyysoft.msdp.dp.sys.entity.SysThird;

/**
 * 系统社交登录账号表
 *
 * @author mao.chi
 * @date 2018-08-16 21:30:41
 */
public interface SysThirdService extends IService<SysThird> {

    /**
     * 绑定社交账号
     *
     * @param state 类型
     * @param code  code
     * @return
     */
    Boolean bindThird(String state, String code);

    /**
     * 根据入参查询用户信息
     *
     * @param inStr
     * @return
     */
    UserInfo getUserInfo(String inStr);

    SysThird getByTypeAppId(String type, String appId);
}

