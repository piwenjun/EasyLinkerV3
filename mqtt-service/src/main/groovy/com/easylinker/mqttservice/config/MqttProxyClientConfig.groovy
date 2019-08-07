package com.easylinker.mqttservice.config

import cn.hutool.json.JSONObject
import com.easylinker.mqttservice.config.mqttclient.handler.MessageHandler
import com.easylinker.mqttservice.config.mqttclient.handler.SubscribeHandler
import com.easylinker.mqttservice.config.mqttclient.sdkcore.EasyLinkerV3MqttProxyClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Configuration
@Component
class MqttProxyClientConfig {
    Logger logger = LoggerFactory.getLogger(getClass())

    @Autowired
    MongoTemplate mongoTemplate

    @Value('${proxy.broker.host}')
    String host

    @Value('${proxy.broker.broker}')
    String broker

    @Value('${proxy.broker.port}')
    int port

    @Bean
    EasyLinkerV3MqttProxyClient mqttClient() {
        EasyLinkerV3MqttProxyClient serverMqttClient = new EasyLinkerV3MqttProxyClient(
                host,
                port,
                "EasyLinkerV3Proxy",
                "EasyLinkerV3Proxy",
                "EasyLinkerV3Proxy")
        serverMqttClient.connect(new MessageHandler() {

            /**
             *{*
             *    topic
             *    payload:{
             *            String token【必填】
             *            String data 【必填】
             *            String unit 【选填】
             *            String info 【选填】
             *    }
             *    retain
             *    dup
             *    messageId
             *}*
             * @param receivedMessage
             */
            @Override
            void messageArrived(JSONObject receivedMessage) {
                println "收到消息:" + receivedMessage
            }

            void onError(Throwable throwable) {
                logger.info("Proxy connect to server failed with error message:" + throwable.message)

            }

            @Override
            void onError(Exception e) {
                e.printStackTrace()

            }

            void onComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
            //连接成功
            void onConnected() {
                /**
                 * Activemq 的监控Topic：ActiveMQ/Advisory/Connection/#
                 * EMQ 的监控Topic：$SYS/brokers/+/clients/+/#
                 *
                 */
                if (broker == "AMQ") {
//                    /**
//                     * 上下线[因为AMQ的topic分隔符不一样，需要兼容，解析器还没写。目前版本不支持]
//                     */
//                    serverMqttClient.subscribe('ActiveMQ.Advisory.Connection.>', 2, new SubscribeHandler() {
//                        @Override
//                        void onSuccess(String topic, int qos) {
//                            logger.info("Proxy subscribe:" + topic + " with QOS:" + qos + " for monitor device offline.")
//
//                        }
//
//                        @Override
//                        void onError(Exception e) {
//                            logger.info("Proxy subscribe failed with error message:" + e.message)
//
//                        }
//                    })
//
//                    //监控消息
//                    serverMqttClient.subscribe('#', 2, new SubscribeHandler() {
//                        @Override
//                        void onSuccess(String topic, int qos) {
//                            logger.info("Proxy subscribe:" + topic + " with QOS:" + qos + " for monitor device offline.")
//
//                        }
//
//                        @Override
//                        void onError(Exception e) {
//                            logger.info("Proxy subscribe failed with error message:" + e.message)
//
//                        }
//                    })
                }
                if (broker == "EMQ") {
                    /**
                     * 上线
                     */
                    serverMqttClient.subscribe('$SYS/brokers/+/clients/+/connected', 2, new SubscribeHandler() {
                        @Override
                        void onSuccess(String topic, int qos) {
                            logger.info("Proxy subscribe:" + topic + " with QOS:" + qos + " for monitor device offline.")

                        }

                        @Override
                        void onError(Exception e) {
                            logger.info("Proxy subscribe failed with error message:" + e.message)

                        }
                    })
                    /**
                     * 下线
                     */
                    serverMqttClient.subscribe('$SYS/brokers/+/clients/+/disconnected', 2, new SubscribeHandler() {
                        @Override
                        void onSuccess(String topic, int qos) {
                            logger.info("Proxy subscribe:" + topic + " with QOS:" + qos + " for monitor device offline.")

                        }

                        @Override
                        void onError(Exception e) {
                            logger.info("Proxy subscribe failed with error message:" + e.message)

                        }
                    })

                    /**
                     * 消息处理
                     */
                    serverMqttClient.subscribe('#', 2, new SubscribeHandler() {
                        /**
                         *
                         * @param topic
                         * @param qos
                         */
                        @Override
                        void onSuccess(String topic, int qos) {
                            logger.info("Proxy subscribe:" + topic + " with QOS:" + qos + " for monitor device offline.")

                        }

                        @Override
                        void onError(Exception e) {
                            logger.info("Proxy subscribe failed with error message:" + e.message)

                        }
                    })
                }

            }


        })
        return serverMqttClient
    }
}
