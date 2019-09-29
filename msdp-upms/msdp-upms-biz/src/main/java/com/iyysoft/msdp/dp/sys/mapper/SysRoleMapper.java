package com.iyysoft.msdp.dp.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iyysoft.msdp.dp.sys.entity.SysRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> listRolesByUserId(String userId);
}
