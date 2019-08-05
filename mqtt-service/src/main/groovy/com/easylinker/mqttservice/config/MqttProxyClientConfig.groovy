package com.easylinker.mqttservice.config


import com.easylinker.mqttservice.config.mqttclient.handler.MessageHandler
import com.easylinker.mqttservice.config.mqttclient.handler.SubscribeHandler
import com.easylinker.mqttservice.config.mqttclient.sdkcore.ServerMqttClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MqttProxyClientConfig {
    Logger logger = LoggerFactory.getLogger(getClass())

    @Autowired
    MongoTemplate mongoTemplate
    @Value('${proxy.mqtt.host}')
    String host
    @Value('${proxy.mqtt.port}')
    int port

    @Bean
    ServerMqttClient mqttClient() {
        ServerMqttClient serverMqttClient = new ServerMqttClient(host,
                1883,
                "clientId",
                "username",
                "password")
        serverMqttClient.connect(new MessageHandler() {

            //出错
            @Override
            void messageArrived(cn.hutool.json.JSONObject receivedMessage) {
                println receivedMessage.toString()
            }

            void onError(Throwable throwable) {

            }
            //发送完成
            void onComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
            //连接成功
            void onConnected() {
                serverMqttClient.subscribe('$SYS/brokers/+/clients/+/disconnected', 2, new SubscribeHandler() {
                    @Override
                    void onSuccess(String topic, int qos) {

                    }

                    @Override
                    void onError(Exception e) {

                    }
                })
                //
                serverMqttClient.subscribe('$SYS/brokers/+/clients/+/connected', 2, new SubscribeHandler() {
                    @Override
                    void onSuccess(String topic, int qos) {

                    }

                    @Override
                    void onError(Exception e) {

                    }
                })
            }


        });
        return serverMqttClient
    }
}
