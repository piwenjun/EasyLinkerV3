package com.easylinker.mqttservice


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ['com.easylinker.*', 'com.easylinker.framework.common.config.*'])

class MqttServiceApplication {


    static void main(String[] args) {
        SpringApplication.run(MqttServiceApplication, args)
    }

}
