package com.iyysoft.msdp.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author mao.chi
 * @date 2018年06月21日
 * 服务中心
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class MsdpEurekaApplication {

    public static void main(String[] args) {
//		ApplicationEventListener listener = new ApplicationEventListener(iyysoftEurekaApplication.class,"iyysoftEureka");
//		if(listener.isRunning()) {
//			log.info("eureka 进程文件已经存在,终止运行!!!");
//			return;
//		}
//		SpringApplication springApplication =new SpringApplication(iyysoftEurekaApplication.class);
//		springApplication.addListeners(listener);
//		springApplication.run(args);
        SpringApplication.run(MsdpEurekaApplication.class, args);
    }
}
