package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.SysUser;
import com.iyysoft.msdp.dp.sys.vo.UserThirdVo;
import com.iyysoft.msdp.dp.sys.mapper.UserThirdMapper;
import com.iyysoft.msdp.dp.sys.service.UserThirdService;
import com.iyysoft.msdp.dp.sys.entity.UserThird;
import org.springframework.stereotype.Service;

/**
 * 第三方登录
 *
 * @author mao.chi
 * @date 2019-04-17 15:52:27
 */
@Service
public class UserThirdServiceImpl extends ServiceImpl<UserThirdMapper, UserThird> implements UserThirdService {
    /**
     * 通过查第三人登陆用户的全部信息
     *
     * @param thirdId
     * @param openId
     * @return
     */
    @Override
    public UserThird getUserInfo(String thirdId, String openId) {
        UserThird userThird = baseMapper.selectOne(
                Wrappers.<UserThird>query().lambda()
                        .eq(UserThird::getThirdId, thirdId)
                        .eq(UserThird::getOpenId, openId));
        return userThird;
    }


    @Override
    public Boolean thirdBind(UserThirdVo userThirdVo) {
        return null;
    }

    @Override
    public int wxminiBind(SysUser user, String thirdId, String openId) {
        UserThird userThird = new UserThird();
        userThird.setThirdId(thirdId);
        userThird.setOpenId(openId);
        userThird.setUserId(user.getUserId());
        return baseMapper.insert(userThird);
    }

    @Override
    public int updateUnionId(String thirdId, String openid, String unionId) {
        return baseMapper.updateUnionId(thirdId, openid, unionId);
    }

//	@Override
//	public String wxLogout(UserThird userThird) {
//		return this.baseMapper.wxLogout(userThird);
//	}
}
