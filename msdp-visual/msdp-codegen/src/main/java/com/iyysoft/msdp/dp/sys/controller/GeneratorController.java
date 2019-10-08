package com.iyysoft.msdp.dp.sys.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.GenConfig;
import com.iyysoft.msdp.dp.sys.service.GeneratorService;
import com.iyysoft.msdp.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成器
 *
 * @author mao.chi
 * @date 2018-07-30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private final GeneratorService generatorService;

    /**
     * 列表
     *
     * @param tableName 参数集
     * @return 数据库表
     */
    @GetMapping("/page")
    public R getPage(Page page, String tableName) {
        return new R<>(generatorService.getPage(page, tableName));
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    @PostMapping("/code")
    public void generatorCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
        byte[] data = generatorService.generatorCode(genConfig);

        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }
}
