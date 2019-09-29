package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iyysoft.msdp.dp.sys.entity.SysMenu;
import com.iyysoft.msdp.dp.sys.entity.SysRoleMenu;
import com.iyysoft.msdp.dp.sys.vo.MenuVo;
import com.iyysoft.msdp.dp.sys.mapper.SysMenuMapper;
import com.iyysoft.msdp.dp.sys.mapper.SysRoleMenuMapper;
import com.iyysoft.msdp.dp.sys.service.SysMenuService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author mao.chi
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Cacheable(value = "menu_details", key = "#roleId  + '_menu'")
    public List<MenuVo> findMenuByRoleId(String roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "menu_details", allEntries = true)
    public R removeMenuById(String id) {
        // 查询父节点为当前节点的节点
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query()
                .lambda().eq(SysMenu::getParentId, id));
        if (CollUtil.isNotEmpty(menuList)) {
            return R.builder()
                    .code(CommonConstants.FAIL)
                    .msg("菜单含有下级不能删除").build();
        }

        sysRoleMenuMapper
                .delete(Wrappers.<SysRoleMenu>query()
                        .lambda().eq(SysRoleMenu::getMenuId, id));

        //删除当前菜单及其子菜单
        return new R(this.removeById(id));
    }

    @Override
    @CacheEvict(value = "menu_details", allEntries = true)
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }
}
