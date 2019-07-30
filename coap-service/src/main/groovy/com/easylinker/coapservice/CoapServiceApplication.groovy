package com.easylinker.coapservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class CoapServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(CoapServiceApplication, args)
    }

}
