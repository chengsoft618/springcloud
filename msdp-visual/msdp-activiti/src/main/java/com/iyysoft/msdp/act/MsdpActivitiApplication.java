package com.iyysoft.msdp.act;

import com.iyysoft.msdp.common.security.annotation.EnableMsdpFeignClients;
import com.iyysoft.msdp.common.security.annotation.EnableMsdpResourceServer;
import com.iyysoft.msdp.common.swagger.annotation.EnableMsdpSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableMsdpSwagger2
@EnableMsdpFeignClients
@EnableMsdpResourceServer
@SpringCloudApplication
public class MsdpActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpActivitiApplication.class, args);
    }

}
