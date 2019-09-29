package com.iyysoft.msdp.dp.sys.service;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iyysoft.msdp.dp.sys.entity.GatewayRoute;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 路由
 *
 * @author mao.chi
 * @date 2018-11-06 10:17:18
 */
public interface GatewayRouteService extends IService<GatewayRoute> {

    /**
     * 获取全部路由
     * <p>
     * RedisRouteDefinitionWriter.java
     * PropertiesRouteDefinitionLocator.java
     *
     * @return
     */
    List<GatewayRoute> routes();

    /**
     * 更新路由信息
     *
     * @param routes 路由信息
     * @return
     */
    Mono<Void> updateRoutes(JSONArray routes);
}

