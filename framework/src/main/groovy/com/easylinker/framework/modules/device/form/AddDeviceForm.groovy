package com.easylinker.framework.modules.device.form

import lombok.Data

import javax.validation.constraints.NotNull

/**
 * @author wwhai* @date 2019/6/7 15:56
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@Data
class AddDeviceForm {
    @NotNull(message = "设备名不能为空")
    private String name
    @NotNull(message = "设备信息不能为空")
    private String info
    @NotNull(message = "设备类型不能为空")

    private String type
    //MQTT
    @NotNull(message = "MQTT服务器不能为空")

    private String mqttServerHost
    @NotNull(message = "MQTT端口不能为空")

    private Integer mqttPort
    @NotNull(message = "Mqtt用户名不能为空")

//    private String mqttUsername
//    @NotNull(message = "Mqtt密码不能为空")
//    private String mqttPassword

    //SSH
    @NotNull(message = "SSH用户不能为空")

    private String sshUser
    @NotNull(message = "SSH密码不能为空")

    private String sshPassword

    String getEchoTopic() {
        return echoTopic
    }

    void setEchoTopic(String echoTopic) {
        this.echoTopic = echoTopic
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

//    String getMqttUsername() {
//        return mqttUsername
//    }
//
//    void setMqttUsername(String mqttUsername) {
//        this.mqttUsername = mqttUsername
//    }
//
//    String getMqttPassword() {
//        return mqttPassword
//    }
//
//    void setMqttPassword(String mqttPassword) {
//        this.mqttPassword = mqttPassword
//    }

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
}
