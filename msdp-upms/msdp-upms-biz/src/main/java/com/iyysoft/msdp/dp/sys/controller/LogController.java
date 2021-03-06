package com.iyysoft.msdp.dp.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.SysLog;
import com.iyysoft.msdp.dp.sys.vo.PreLogVo;
import com.iyysoft.msdp.dp.sys.service.SysLogService;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author mao.chi
 * @since 2017-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Api(value = "log", tags = "日志管理模块")
public class LogController {
    private final SysLogService sysLogService;

    /**
     * 简单分页查询
     *
     * @param page   分页对象
     * @param sysLog 系统日志
     * @return
     */
    @GetMapping("/page")
    public R getLogPage(Page page, SysLog sysLog) {
        return new R<>(
                sysLogService.page(page, Wrappers.query(sysLog))
        );
    }

    /**
     * 删除日志
     *
     * @param logId ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_log_del')")
    public R removeById(@PathVariable String logId) {
        return new R<>(sysLogService.removeById(logId));
    }

    /**
     * 插入日志
     *
     * @param sysLog 日志实体
     * @return success/false
     */
    @Inner
    @PostMapping("/save")
    public R save(@Valid @RequestBody SysLog sysLog) {
        return new R<>(sysLogService.save(sysLog));
    }

    /**
     * 批量插入前端异常日志
     *
     * @param preLogVoList 日志实体
     * @return success/false
     */
    @PostMapping("/logs")
    public R saveBatchLogs(@RequestBody List<PreLogVo> preLogVoList) {
        return new R<>(sysLogService.saveBatchLogs(preLogVoList));
    }
}
