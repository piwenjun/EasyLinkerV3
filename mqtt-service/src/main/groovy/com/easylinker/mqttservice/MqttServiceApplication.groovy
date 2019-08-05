package com.easylinker.mqttservice


import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MqttServiceApplication implements CommandLineRunner {


    static void main(String[] args) {
        SpringApplication.run(MqttServiceApplication, args)
    }

    @Override
    void run(String... args) throws Exception {

    }
}
