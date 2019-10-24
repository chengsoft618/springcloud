package com.iyysoft.msdp.dp.app;

import com.iyysoft.msdp.common.security.annotation.EnableMsdpFeignClients;
import com.iyysoft.msdp.common.security.annotation.EnableMsdpResourceServer;
import com.iyysoft.msdp.common.swagger.annotation.EnableMsdpSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author mao.chi
 * @date 2019年08月21日
 * 统一应用管理中心
 */

@EnableMsdpSwagger2
@SpringCloudApplication
@EnableMsdpFeignClients
@EnableMsdpResourceServer
public class MsdpAppBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpAppBizApplication.class, args);
    }

}
