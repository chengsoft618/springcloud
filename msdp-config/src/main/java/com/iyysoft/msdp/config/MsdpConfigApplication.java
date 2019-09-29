package com.iyysoft.msdp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author mao.chi
 * @date 2018年06月22日
 * 配置中心
 */
@EnableConfigServer
//@EnableDiscoveryClient //因为内部构造问题  @EnableDiscoveryClient 中@EnableDiscoveryClient不起作用
@SpringCloudApplication
public class MsdpConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpConfigApplication.class, args);
    }
}
