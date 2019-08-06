package com.easylinker.mqttservice.config.mqttclient.handler;

import cn.hutool.json.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

/**
 * 消息处理器
 */
public abstract class MessageHandler implements MqttCallback {
    /**
     * 异常处理
     *
     * @param throwable
     */
    public void connectionLost(Throwable throwable) {
        onError(throwable);

    }

    /**
     * @param topic       mqttTopic
     * @param mqttMessage Message
     * @throws Exception
     */
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("收到的消息类型:" + mqttMessage.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("topic", topic);
        jsonObject.put("payload", new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
        jsonObject.put("retain", mqttMessage.isRetained());
        jsonObject.put("dup", mqttMessage.isDuplicate());
        jsonObject.put("messageId", mqttMessage.getId());
        messageArrived(jsonObject);
    }

    /**
     * 后续步骤
     *
     * @param iMqttDeliveryToken MessageID管理类
     */

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        onComplete(iMqttDeliveryToken);
    }

    /**
     * 消息到达封装JSON
     */
    public abstract void messageArrived(JSONObject receivedMessage);

    /**
     * 出错处理
     */
    public abstract void onError(Throwable throwable);

    /**
     * 出错处理
     */
    public abstract void onError(Exception e);

    /**
     *
     */
    public abstract void onComplete(IMqttDeliveryToken iMqttDeliveryToken);

    /**
     *
     */
    public abstract void onConnected();
}
