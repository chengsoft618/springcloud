package com.iyysoft.msdp.auth;


import com.iyysoft.msdp.common.security.annotation.EnableMsdpFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author mao.chi
 * @date 2018年06月21日
 * 认证授权中心
 */
@SpringCloudApplication
@EnableMsdpFeignClients
public class MsdpAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpAuthApplication.class, args);
    }
}
