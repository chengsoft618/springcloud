package com.iyysoft.msdp.dp.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.entity.SysUserThird;
import com.iyysoft.msdp.dp.sys.vo.UserThirdVo;


/**
 * 第三方登录
 *
 * @author waylen.chi
 * @date 2019-04-17 15:52:27
 */
public interface SysUserThirdService extends IService<SysUserThird> {

    /**
     * 查询用户信息
     *
     * @param type
     * @param weixinId
     * @return
     */
    SysUserThird getUserInfo(String type, String weixinId);

    Boolean thirdBind(UserThirdVo userThirdVo);

    int wxminiBind(SysUser user, String thirdId, String openid);

    int updateUnionId(String thirdId, String openid, String unionId);

//	/**
//	 * 退出微信登录
//	 * @param sysUserThird
//	 * @return
//	 */
//	String wxLogout(SysUserThird sysUserThird);
}
