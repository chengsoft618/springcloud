
package com.iyysoft.msdp.dp.sys;

import com.iyysoft.msdp.common.security.annotation.EnableMsdpFeignClients;
import com.iyysoft.msdp.common.security.annotation.EnableMsdpResourceServer;
import com.iyysoft.msdp.common.swagger.annotation.EnableMsdpSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author mao.chi
 * @date 2018/07/29
 * 代码生成模块
 *
 * com.iyysoft.msdp.dp.sys.org
 * com.iyysoft.msdp.edu.hr.org
 * com.iyysoft.msdp.edu.app.org
 */
//@MapperScan(basePackages = {"com.iyysoft"})
@EnableMsdpSwagger2
@SpringCloudApplication
@EnableMsdpFeignClients
@EnableMsdpResourceServer
public class MsdpCodeGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsdpCodeGenApplication.class, args);
    }
}
