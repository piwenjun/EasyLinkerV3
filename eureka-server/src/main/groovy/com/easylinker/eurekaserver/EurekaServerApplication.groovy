package com.easylinker.eurekaserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class EurekaServerApplication {

    static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication, args)
    }

}
