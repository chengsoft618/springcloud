package com.iyysoft.msdp.dp.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iyysoft.msdp.dp.sys.entity.SysUserThird;
import org.apache.ibatis.annotations.Param;


/**
 * 第三方登录
 *
 * @author waylen.chi
 * @date 2019-04-17 15:52:27
 */
public interface SysUserThirdMapper extends BaseMapper<SysUserThird> {
    Integer removeOpenId(@Param("thirdId") String thirdId, @Param("openId") String openId);

    Integer updateUnionId(@Param("thirdId") String thirdId, @Param("openId") String openId, @Param("unionId") String unionId);

//	/**
//	 * 退出微信登录
//	 * @param sysUserThird
//	 * @return
//	 */
//	String wxLogout(SysUserThird sysUserThird);

}
