package com.iyysoft.msdp.dp.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.SysUserRole;
import com.iyysoft.msdp.dp.sys.mapper.SysUserRoleMapper;
import com.iyysoft.msdp.dp.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    @Override
    public Boolean deleteByUserId(String userId) {
        return baseMapper.deleteByUserId(userId);
    }
}
