
package com.iyysoft.msdp.gateway;

import com.iyysoft.msdp.common.gateway.annotation.EnableMsdpDynamicRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author mao.chi
 * @date 2019年06月21日
 * 网关应用
 */
@EnableMsdpDynamicRoute
@SpringCloudApplication
public class MsdpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpGatewayApplication.class, args);
    }
}
