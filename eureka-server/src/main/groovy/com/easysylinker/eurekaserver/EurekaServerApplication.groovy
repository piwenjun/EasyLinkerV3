package com.easysylinker.eurekaserver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaServerApplication {

    static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication, args)
    }

}
