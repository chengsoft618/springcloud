package com.iyysoft.msdp.dp.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import org.apache.ibatis.annotations.Param;

/**
 * 第三方登录
 *
 * @author mao.chi
 * @date 2019-04-17 15:52:27
 */
public interface UserThirdMapper extends BaseMapper<UserThird> {
    Integer removeOpenId(@Param("thirdId") String thirdId, @Param("openId") String openId);

    Integer updateUnionId(@Param("thirdId") String thirdId, @Param("openId") String openId, @Param("unionId") String unionId);

//	/**
//	 * 退出微信登录
//	 * @param userThird
//	 * @return
//	 */
//	String wxLogout(UserThird userThird);

}
