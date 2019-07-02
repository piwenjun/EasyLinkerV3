package com.easylinker.v3.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.v3.modules.scene.model.Scene

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

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
    private String topic
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private AppUser appUser
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private Scene scene

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

    String getTopic() {
        return topic
    }

    void setTopic(String topic) {
        this.topic = topic
    }
}
