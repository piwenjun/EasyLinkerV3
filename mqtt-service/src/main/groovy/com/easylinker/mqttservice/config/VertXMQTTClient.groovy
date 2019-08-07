//package com.easylinker.mqttservice.config
//
//import com.alibaba.fastjson.JSONObject
//import com.easylinker.framework.common.model.DeviceData
//import com.easylinker.framework.utils.DeviceTokenUtils
//import io.vertx.core.AbstractVerticle
//import io.vertx.core.Vertx
//import io.vertx.mqtt.MqttClient
//import io.vertx.mqtt.MqttClientOptions
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.stereotype.Component
//
//@Component
//class VertXMqttClientVerticle extends AbstractVerticle {
//    @Autowired
//    MongoTemplate mongoTemplate
//    Logger logger = LoggerFactory.getLogger(getClass())
//    @Value('${proxy.mqtt.host}')
//    String host
//    @Value('${proxy.mqtt.port}')
//    int port
//
//    @Override
//    void start() throws Exception {
//        logger.info("Mqtt Proxy Starting.....")
//        MqttClientOptions options = new MqttClientOptions();
//        options.setUsername("V3")
//        options.setPassword("V3")
//        options.setClientId("V3")
//        options.setMaxMessageSize(100_000_000)
//        MqttClient client = MqttClient.create(Vertx.vertx(), options)
//        client.connect(port, host, { connectHandler ->
//            if (client.connected) {
//                logger.info("Mqtt proxy success  connected server:" + host + ":" + port)
//
//                /**
//                 * String token【必填】
//                 * String data 【必填】
//                 * String unit 【选填】
//                 * String info 【选填】
//                 * 数据形式
//                 *{*
//                 *     token:密钥
//                 *     data:数据
//                 *     unit:单位
//                 *     info:备注
//                 *}*/
//
//                //V3可以订阅所有Topic
//                client.publishHandler({ s ->
//                    println "收到消息,TOPIC:" + s.topicName() + ";MSG:" + s.payload().toString()
//
//                    if (s.payload().length() > 1024) {
//                        logger.info("Data size too long!")
//                        return
//                    }
//                    JSONObject dataJson
//                    try {
//                        dataJson = JSONObject.parseObject(s.payload().toString())
//                    } catch (Exception e) {
//
//                        logger.error(e.message)
//                        return
//                    }
//                    String token = dataJson.getString("token")
//                    String data = dataJson.getString("data")
//
//                    if ((!token)) {
//                        return
//                    }
//                    if (!data) {
//                        return
//                    }
//                    String[] tokenList
//                    try {
//                        //Token解析出来是这样的:[0cc33895a5cd46feb9483a02ca52c5d7, BOOLEAN]
//                        tokenList = DeviceTokenUtils.getInfo(DeviceTokenUtils.parse(token))
//                    } catch (Exception e) {
//                        logger.error(e.message)
//                        return
//                    }
//                    String unit = dataJson.getString("unit")
//                    String info = dataJson.getString("info")
//
//                    //全部过滤
//                    String deviceSecurityId = tokenList[0]
//                    String deviceType = tokenList[1]
//                    //生成数据
//                    DeviceData deviceData = new DeviceData(
//                            deviceType: deviceType,
//                            securityId: UUID.randomUUID().toString().replace("-", ""),
//                            createTime: new Date(),
//                            updateTime: new Date(),
//                            data: data,
//                            unit: unit ? "" : unit,
//                            deviceSecurityId: deviceSecurityId,
//                            info: info ? "" : info
//                    )
//                    mongoTemplate.save(deviceData, "DEVICE_DATA")
//                }).subscribe('111', 2)
//                //
//                client.publishHandler({ s ->
//                    println "上线:" + s.topicName() + "---MSG:" + s.payload().toString()
//
//                }).subscribe('$SYS/brokers/+/clients/+/connected', 2)
//                //
//                client.publishHandler({ s ->
//                    println "掉线:" + s.topicName() + "---MSG:" + s.payload().toString()
//
//                }).subscribe('$SYS/brokers/+/clients/+/disconnected', 2)
//
//            } else {
//                logger.info("Mqtt proxy failed to connected server:" + host + ":" + port)
//
//            }
//
//
//        })
//
//
//    }
//
//}