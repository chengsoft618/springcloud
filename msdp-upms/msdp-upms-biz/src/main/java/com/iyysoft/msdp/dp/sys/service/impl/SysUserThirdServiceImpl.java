package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.*;
import com.iyysoft.msdp.dp.sys.mapper.SysUserThirdMapper;
import com.iyysoft.msdp.dp.sys.service.SysUserThirdService;
import com.iyysoft.msdp.dp.sys.vo.UserThirdVo;
import org.springframework.stereotype.Service;

/**
 * 第三方登录
 *
 * @author mao.chi
 * @date 2019-04-17 15:52:27
 */
@Service
public class SysUserThirdServiceImpl extends ServiceImpl<SysUserThirdMapper, SysUserThird> implements SysUserThirdService {
    /**
     * 通过查第三人登陆用户的全部信息
     *
     * @param thirdId
     * @param openId
     * @return
     */
    @Override
    public SysUserThird getUserInfo(String thirdId, String openId) {
        SysUserThird userThird = baseMapper.selectOne(
                Wrappers.<SysUserThird>query().lambda()
                        .eq(SysUserThird::getThirdId, thirdId)
                        .eq(SysUserThird::getOpenId, openId));
        return userThird;
    }

    @Override
    public Boolean thirdBind(UserThirdVo userThirdVo) {
        return null;
    }

    @Override
    public int wxminiBind(SysUser user, String thirdId, String openId) {
        SysUserThird userThird = new SysUserThird();
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
//	public String wxLogout(SysUserThird userThird) {
//		return this.baseMapper.wxLogout(userThird);
//	}
}
