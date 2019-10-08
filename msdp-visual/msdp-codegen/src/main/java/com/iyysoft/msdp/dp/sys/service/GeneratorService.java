package com.iyysoft.msdp.dp.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.GenConfig;

import java.util.List;
import java.util.Map;

/**
 * @author mao.chi
 * @date 2019/7/29
 */
public interface GeneratorService {
    /**
     * 生成代码
     *
     * @param tableNames 表名称
     * @return
     */
    byte[] generatorCode(GenConfig tableNames);

    /**
     * 分页查询表
     *
     * @param tableName 表名
     * @return
     */
    IPage<List<Map<String, Object>>> getPage(Page page, String tableName);
}
