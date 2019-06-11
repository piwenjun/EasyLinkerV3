package com.easylinker.framework.modules.device.model

import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.modules.user.model.AppUser
import lombok.Data

import javax.persistence.*

/**
 * 终端设备
 * 0.1版本 只支持Linux
 * 后面考虑支持windows和VNC
 */
@Entity
@Data
class Device extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //基本信息
    Long id
    private String name
    private String info
    private String type
    private String echoTopic
    //MQTT
    private String mqttServerHost
    private Integer mqttPort
    private String mqttUsername
    private String mqttPassword
    private String topicKey

    //SSH
    private String sshUser
    private String sshPassword

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getMqttServerHost() {
        return mqttServerHost
    }

    void setMqttServerHost(String mqttServerHost) {
        this.mqttServerHost = mqttServerHost
    }

    Integer getMqttPort() {
        return mqttPort
    }

    void setMqttPort(Integer mqttPort) {
        this.mqttPort = mqttPort
    }

    String getMqttUsername() {
        return mqttUsername
    }

    void setMqttUsername(String mqttUsername) {
        this.mqttUsername = mqttUsername
    }

    String getMqttPassword() {
        return mqttPassword
    }

    void setMqttPassword(String mqttPassword) {
        this.mqttPassword = mqttPassword
    }

    String getTopicKey() {
        return topicKey
    }

    void setTopicKey(String topicKey) {
        this.topicKey = topicKey
    }

    String getSshUser() {
        return sshUser
    }

    void setSshUser(String sshUser) {
        this.sshUser = sshUser
    }

    String getSshPassword() {
        return sshPassword
    }

    void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword
    }

    String getEchoTopic() {
        return echoTopic
    }

    void setEchoTopic(String echoTopic) {
        this.echoTopic = echoTopic
    }
}
