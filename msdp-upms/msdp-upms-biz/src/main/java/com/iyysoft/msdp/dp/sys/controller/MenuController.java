package com.iyysoft.msdp.dp.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iyysoft.msdp.common.security.util.MsdpSecurityUtils;
import com.iyysoft.msdp.dp.sys.dto.MenuTree;
import com.iyysoft.msdp.dp.sys.entity.SysMenu;
import com.iyysoft.msdp.dp.sys.vo.MenuVo;
import com.iyysoft.msdp.dp.sys.vo.TreeUtil;
import com.iyysoft.msdp.dp.sys.service.SysMenuService;
import com.iyysoft.msdp.common.core.constant.CommonConstants;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mao.chi
 * @date 2017/10/31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "menu", tags = "菜单管理模块")
public class MenuController {
    private final SysMenuService sysMenuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R getUserMenu() {
        // 获取符合条件的菜单
        Set<MenuVo> all = new HashSet<>();
        MsdpSecurityUtils.getRoles()
                .forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> CommonConstants.MENU.equals(menuVo.getType()))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        return new R<>(TreeUtil.build(menuTreeList, "-1"));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R getTree() {
        return new R<>(TreeUtil.buildTree(sysMenuService.list(Wrappers.emptyWrapper()), "-1"));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/tree/{roleId}")
    public List getRoleTree(@PathVariable String roleId) {
        return sysMenuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVo::getMenuId)
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return new R<>(sysMenuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @SysLog("新增菜单")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_add')")
    public R save(@Valid @RequestBody SysMenu sysMenu) {
        return new R<>(sysMenuService.save(sysMenu));
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @SysLog("删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R removeById(@PathVariable String id) {
        return sysMenuService.removeMenuById(id);
    }

    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @SysLog("更新菜单")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_edit')")
    public R update(@Valid @RequestBody SysMenu sysMenu) {
        return new R<>(sysMenuService.updateMenuById(sysMenu));
    }

}
