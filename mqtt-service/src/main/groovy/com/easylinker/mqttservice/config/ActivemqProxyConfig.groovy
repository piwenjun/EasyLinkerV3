//package com.easylinker.mqttservice.config
//
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.apache.activemq.command.ActiveMQMessage
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.jms.annotation.EnableJms
//import org.springframework.jms.annotation.JmsListener
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory
//import org.springframework.jms.config.JmsListenerContainerFactory
//import org.springframework.stereotype.Service
//
//import javax.jms.ConnectionFactory
//
///**
// * 配置类
// */
//@Configuration
//@EnableJms
//class ActiveMQConfig {
//
//    @Value('${spring.activemq.user}')
//    private String userName
//
//    @Value('${spring.activemq.password}')
//    private String password
//
//    @Value('${spring.activemq.broker-url}')
//    private String brokerUrl
//
//    @Bean
//    ConnectionFactory connectionFactory() {
//        ActiveMQConnectionFactory connectionFactory =
//                new ActiveMQConnectionFactory()
//        connectionFactory.setBrokerURL(brokerUrl)
//        connectionFactory.setPassword(password)
//        connectionFactory.setUserName(userName)
//        return connectionFactory
//    }
//
//    @Bean("ActivemqTopicFactory")
//    JmsListenerContainerFactory topicFactory() {
//        DefaultJmsListenerContainerFactory factory =
//                new DefaultJmsListenerContainerFactory()
//        factory.setConnectionFactory(connectionFactory())
//        //开启topic模式
//        factory.setPubSubDomain(true)
//        return factory
//    }
//
//}
//
//@Service
//class ActiveMsgListener {
//
//    @JmsListener(destination = "ActiveMQ.Advisory.Connection",containerFactory = 'ActivemqTopicFactory')
//    void receive(ActiveMQMessage message) {
//        System.out.println("activemq的数据" + message.getMessage())
//    }
//
//}