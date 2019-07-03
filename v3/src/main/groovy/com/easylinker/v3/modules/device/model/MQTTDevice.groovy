package com.easylinker.v3.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.scene.model.Scene

import javax.persistence.*

/**
 * @author wwhai* @date 2019/6/29 22:58
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
class MQTTDevice extends AbstractDevice {
    private String username
    private String password
    private String clientId
    private Boolean online

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "mqttDevice")
    private List<TopicAcl> topicAcls
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private AppUser appUser
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Scene scene

    List<TopicAcl> getTopicAcls() {
        return topicAcls
    }

    void setTopicAcls(List<TopicAcl> topicAcls) {
        this.topicAcls = topicAcls
    }

    Boolean getOnline() {
        return online
    }

    void setOnline(Boolean online) {
        this.online = online
    }

    String getClientId() {
        return clientId
    }

    void setClientId(String clientId) {
        this.clientId = clientId
    }

    Scene getScene() {
        return scene
    }

    void setScene(Scene scene) {
        this.scene = scene
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

}
/**
 * MQTT设备的ACL规则
 */
@Entity
class TopicAcl extends AbstractModel {
    private String ip
    private String username
    private String clientId
    private String topic
    /**
     * 1 PUB
     * 2 SUB
     * 3 ALL
     */
    private int access
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private MQTTDevice mqttDevice

    String getIp() {
        return ip
    }

    void setIp(String ip) {
        this.ip = ip
    }

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getClientId() {
        return clientId
    }

    void setClientId(String clientId) {
        this.clientId = clientId
    }

    String getTopic() {
        return topic
    }

    void setTopic(String topic) {
        this.topic = topic
    }

    int getAccess() {
        return access
    }

    void setAccess(int access) {
        this.access = access
    }

    MQTTDevice getMqttDevice() {
        return mqttDevice
    }

    void setMqttDevice(MQTTDevice mqttDevice) {
        this.mqttDevice = mqttDevice
    }
}
