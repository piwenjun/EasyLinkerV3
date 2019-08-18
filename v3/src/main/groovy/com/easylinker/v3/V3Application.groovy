package com.easylinker.v3


import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

//@EnableEurekaClient

@SpringBootApplication
//当有多个模块的时候，这里需要配置包路径，不然SpringBoot默认扫描当前模块
@ComponentScan(basePackages = ['com.easylinker.*', 'com.easylinker.framework.common.config.*'])
class V3Application implements CommandLineRunner {

    @Autowired
    Scheduler scheduler

    static void main(String[] args) {
        SpringApplication.run(V3Application, args)
    }

    @Override
    void run(String... args) throws Exception {

    }

}
