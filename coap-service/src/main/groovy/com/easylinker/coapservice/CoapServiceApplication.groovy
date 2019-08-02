package com.easylinker.coapservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//@EnableEurekaClient
@ComponentScan(basePackages = ['com.easylinker.*', 'com.easylinker.framework.common.config.*'])
class CoapServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(CoapServiceApplication, args)
    }

}
