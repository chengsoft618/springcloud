package com.iyysoft.msdp.dp.sys;


import com.iyysoft.msdp.common.security.annotation.EnableMsdpFeignClients;
import com.iyysoft.msdp.common.security.annotation.EnableMsdpResourceServer;
import com.iyysoft.msdp.common.swagger.annotation.EnableMsdpSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author mao.chi
 * @date 2018年06月21日
 * 用户统一管理系统
 */
@EnableMsdpSwagger2
@SpringCloudApplication
@EnableMsdpFeignClients
@EnableMsdpResourceServer
public class MsdpAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsdpAdminApplication.class, args);
    }

}
