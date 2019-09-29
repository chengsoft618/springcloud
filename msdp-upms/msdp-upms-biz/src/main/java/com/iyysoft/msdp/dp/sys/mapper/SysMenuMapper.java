package com.iyysoft.msdp.dp.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iyysoft.msdp.dp.sys.entity.SysMenu;
import com.iyysoft.msdp.dp.sys.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<MenuVo> listMenusByRoleId(String roleId);

    /**
     * 通过角色ID查询权限
     *
     * @param roleIds Ids
     * @return
     */
    List<String> listPermissionsByRoleIds(String roleIds);
}
