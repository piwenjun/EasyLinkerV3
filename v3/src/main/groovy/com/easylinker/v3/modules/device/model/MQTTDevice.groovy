package com.easylinker.v3.modules.device.model

import com.easylinker.v3.modules.model.AbstractDevice
import com.easylinker.v3.modules.model.AbstractModel
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import com.fasterxml.jackson.annotation.JsonIgnore

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

    private boolean online

    private boolean isSuperUser

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "mqttDevice")
    @JsonIgnore
    private List<TopicAcl> topicAcls
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    private AppUser appUser
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Scene scene

    void setOnline(boolean online) {
        this.online = online
    }

    boolean getIsSuperUser() {
        return isSuperUser
    }

    void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser
    }

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
    //0: deny, 1: allow
    private int allow = 1
    //1: subscribe, 2: publish, 3: pubsub
    private int access = 0

    int getAllow() {
        return allow
    }

    void setAllow(int allow) {
        this.allow = allow
    }


    @JsonIgnore
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


