package com.iyysoft.msdp.dp.sys.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iyysoft.msdp.dp.sys.entity.GenConfig;
import com.iyysoft.msdp.dp.sys.mapper.GeneratorMapper;
import com.iyysoft.msdp.dp.sys.service.GeneratorService;
import com.iyysoft.msdp.dp.sys.util.GenUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author mao.chi
 * @date 2019-07-30
 */
@Service
@Repository
@AllArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorMapper generatorMapper;


    /**
     * 分页查询表
     *
     * @param tableName 查询条件
     * @return
     */
    @Override
    public IPage<List<Map<String, Object>>> getPage(Page page, String tableName) {
        return generatorMapper.queryList(page, tableName);
    }

    /**
     * 生成代码
     *
     * @param genConfig 生成配置
     * @return
     */
    @Override
    public byte[] generatorCode(GenConfig genConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //查询表信息
        Map<String, String> table = queryTable(genConfig.getTableName());
        //查询列信息
        List<Map<String, String>> columns = queryColumns(genConfig.getTableName());
        //生成代码
        GenUtils.generatorCode(genConfig, table, columns, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    private Map<String, String> queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    private List<Map<String, String>> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }
}
