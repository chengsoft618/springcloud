package com.iyysoft.msdp.common.core.util.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ApplicationEventListener
 *
 * @author: mao.chi
 * @date: 2018/10/22
 */
@Slf4j
public class ApplicationEventListener implements ApplicationListener {
    private String appName;
    private String mainPath = "";

    public String getMainPath() {
        return mainPath;
    }

    public ApplicationEventListener(Class mainClass, String appName) {
        this.appName = appName;
        String url = mainClass.getProtectionDomain().getCodeSource().getLocation().toString();
        Path dir = Paths.get(url);
        Path parentDir = dir.getParent();
        if (!parentDir.endsWith("target")) { //测试，生产运行环境
            //url  jar:file:/apps/pekall/ass/service/depmon/libs/depmon.jar!/BOOT-INF/classes!/
            mainPath = parentDir.getParent().getParent().getParent().toString();
            //jar包中的路径字符串包含jar:file:前缀的9个字符，所以要去掉
            mainPath = mainPath.substring(9);
        } else { //本地调试开发环境
            //url  file:/home/maxl/pekall_work/mdm_ass/server/eureka/target/classes/
            mainPath = parentDir.getParent().toString();
        }
        log.info("class路径:" + url);
        log.info("mainPath:" + mainPath);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.info("初始化环境变量完成");
        } else if (event instanceof ApplicationPreparedEvent) {
            log.info("初始化完成");
        } else if (event instanceof ContextRefreshedEvent) {
            log.info("应用刷新完成");
        } else if (event instanceof ApplicationReadyEvent) {
            //应用名从构造参数传递,不自动获取,因为spring.application.name可能与最终打包的appName.jar包名不一样
            //ConfigurableApplicationContext configContext = ((ApplicationReadyEvent) event).getApplicationContext();
            //ConfigurableEnvironment env =configContext.getEnvironment();
            //String appName = env.getProperty("spring.application.name");
            String pidFile = getPidFileFullPath();
            String pid = CreatePidFile(pidFile);
            if (!pidFile.equals("")) {
                log.info("应用启动完成，写进程id文件:" + pidFile + " 进程id:" + pid);
            } else {
                log.info("应用启动完成，写进程id文件失败");
            }

        } else if (event instanceof ContextStartedEvent) {
            log.info("应用启动完成，需要在代码动态添加监听器才可捕获");
        } else if (event instanceof ContextStoppedEvent) {
            log.info("应用停止完成");
        } else if (event instanceof ContextClosedEvent) {
            //删除进程ID文件,由于是异步动作，外部脚本不好估算时间，
            // 加上kafka-client开了另外的线程，不好估计时间，所以不删除pid文件
            //deletePidFile();
            //单独的关闭应用端口去掉
            log.info("应用关闭完成");
        }
    }

    private String getPidFileFullPath() {
//        URL url = this.getClass().getResource("/application.properties");
//        Path dir = Paths.get(url.getPath());
//        Path parentDir = dir.getParent().getParent();
//
//        if(!parentDir.endsWith("target")) { //测试，生产运行环境
//            //url:/file:/apps/pekall/ass/service/eureka/libs/eureka.jar!/BOOT-INF/classes!/application.properties
//            mainPath =  parentDir.getParent().getParent().getParent().toString();
//            //jar包中的路径字符串包含file:前缀的5个字符，所以要去掉
//            mainPath = mainPath.substring(5);
//        }
//        else { //本地调试开发环境
//            //url:file:/home/maxl/pekall_work/mdm_ass/server/eureka/target/classes/application.properties
//            mainPath = parentDir.getParent().toString();
//        }
        return mainPath + File.separator + appName + ".pid";
    }

    private String CreatePidFile(String pidFileFullPath) {
        // get name representing the running Java virtual machine.
        //25107@hostname
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // get pid
        String pid = name.split("@")[0];
        Path path = Paths.get(pidFileFullPath);
        try {
            //Files.createDirectories(path.getParent());
            Files.write(path, pid.getBytes());
            return pid;
        } catch (Exception e) {
            log.info(e.toString());
            return "";
        }


    }

    private void deletePidFile() {
        String pidFile = getPidFileFullPath();
        Path filePath = Paths.get(pidFile);

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

    public boolean isRunning() {
        String pidFile = getPidFileFullPath();
        Path filePath = Paths.get(pidFile);
        return Files.exists(filePath);
    }
}
