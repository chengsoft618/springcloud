package com.iyysoft.msdp.act.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.activiti.engine.repository.Model;

import java.util.Map;

/**
 * @author mao.chi
 * @date 2019/9/25
 */
public interface ModelService {

    /**
     * 创建流程
     *
     * @param name
     * @param key
     * @param desc
     * @param category
     * @return
     */
    Model create(String name, String key, String desc, String category);

    /**
     * 分页获取流程
     *
     * @param params
     * @return
     */
    IPage<Model> getModelPage(Map<String, Object> params);

    /**
     * 删除流程
     *
     * @param id
     * @return
     */
    Boolean removeModelById(String id);

    /**
     * 部署流程
     *
     * @param id
     * @return
     */
    Boolean deploy(String id);
}
