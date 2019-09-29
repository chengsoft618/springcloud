package com.iyysoft.msdp.dp.sys.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.SysThird;
import com.iyysoft.msdp.dp.sys.service.SysThirdService;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import com.iyysoft.msdp.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 系统社交登录账号表
 *
 * @author mao.chi
 * @date 2018-08-16 21:30:41
 */
@RestController
@RequestMapping("/third")
@AllArgsConstructor
@Api(value = "third", tags = "三方账号管理模块")
public class SysThirdController {
    private final SysThirdService thirdService;


    /**
     * 社交登录账户简单分页查询
     *
     * @param page  分页对象
     * @param third 社交登录
     * @return
     */
    @GetMapping("/page")
    public R getThirdPage(Page page, SysThird third) {
        return new R<>(thirdService.page(page, Wrappers.query(third)));
    }


    /**
     * 信息
     *
     * @param thirdId
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") String thirdId) {
        return new R<>(thirdService.getById(thirdId));
    }

    /**
     * 保存
     *
     * @param sysThird
     * @return R
     */
    @SysLog("保存三方信息")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_third_add')")
    public R save(@Valid @RequestBody SysThird sysThird) {
        return new R<>(thirdService.save(sysThird));
    }

    /**
     * 修改
     *
     * @param sysThird
     * @return R
     */
    @SysLog("修改三方信息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_third_edit')")
    public R updateById(@Valid @RequestBody SysThird sysThird) {
        thirdService.updateById(sysThird);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 删除
     *
     * @param thirdId
     * @return R
     */
    @SysLog("删除三方信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_third_del')")
    public R removeById(@PathVariable String thirdId) {
        return new R<>(thirdService.removeById(thirdId));
    }

    /**
     * 通过社交账号、手机号查询用户、角色信息
     *
     * @param inStr appid@code
     * @return
     */
    @Inner
    @GetMapping("/info/{inStr}")
    public R getUserInfo(@PathVariable String inStr) {
        return new R<>(thirdService.getUserInfo(inStr));
    }

}
