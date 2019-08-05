package com.easylinker.mqttservice.config.mqttclient.sdkcore;


import cn.hutool.json.JSONObject;
import com.easylinker.mqttservice.config.mqttclient.bean.PublishBean;
import com.easylinker.mqttservice.config.mqttclient.handler.PublishHandler;
import com.easylinker.mqttservice.config.mqttclient.handler.SubscribeHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author wwhai
 * 服务器端的SDK
 */
public interface IServerMqttClient {

    /**
     * @param topic
     * @param qos
     * @return
     */
    boolean subscribe(String topic, int qos, SubscribeHandler subscribeHandler);

    /**
     * @param topic
     * @return
     */
    boolean unSubscribe(String topic);

    /**
     * @param topic
     * @param message
     * @return
     */
    boolean publish(String topic, MqttMessage message, PublishHandler publishHandler);

    /**
     * @param topic
     * @param message
     * @return
     */
    boolean publishJson(String topic, JSONObject message, PublishHandler publishHandler);

    /**
     * @param topic
     * @param bytes
     * @return
     */
    boolean publishHex(String topic, byte[] bytes, PublishHandler publishHandler);

    /**
     * publish
     */
    boolean publish(PublishBean publishBean, PublishHandler publishHandler);

}
