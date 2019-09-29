package com.iyysoft.msdp.dp.sys.controller;

import cn.hutool.json.JSONArray;
import com.iyysoft.msdp.dp.sys.service.GatewayRouteService;
import com.iyysoft.msdp.common.core.util.R;
import com.iyysoft.msdp.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 路由
 *
 * @author mao.chi
 * @date 2018-11-06 10:17:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Api(value = "route", tags = "动态路由管理模块")
public class GatewayRouteController {
    private final GatewayRouteService gatewayRouteService;


    /**
     * 获取当前定义的路由信息
     *
     * @return
     */
    @GetMapping
    public R listRoutes() {
        return new R<>(gatewayRouteService.list());
    }

    /**
     * 修改路由
     *
     * @param routes 路由定义
     * @return
     */
    @SysLog("修改路由")
    @PutMapping
    public R updateRoutes(@RequestBody JSONArray routes) {
        return new R(gatewayRouteService.updateRoutes(routes));
    }

}
