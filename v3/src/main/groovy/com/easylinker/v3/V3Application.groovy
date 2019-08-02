package com.easylinker.v3

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

@EnableEurekaClient

@SpringBootApplication
//当有多个模块的时候，这里需要配置包路径，不然SpringBoot默认扫描当前模块
@ComponentScan(basePackages = ['com.easylinker.*', 'com.easylinker.framework.common.config.*'])
class V3Application {

    static void main(String[] args) {
        SpringApplication.run(V3Application, args)
    }

}
