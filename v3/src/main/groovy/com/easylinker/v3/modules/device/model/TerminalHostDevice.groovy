package com.easylinker.v3.modules.device.model

import com.easylinker.v3.common.model.AbstractDevice
import com.easylinker.v3.modules.scene.model.Scene
import com.easylinker.v3.modules.user.model.AppUser
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

/**
 * 终端类型的设备
 */
@Entity
class TerminalHostDevice extends AbstractDevice{
    private String sshUsername
    private String sshPassword
    private String sshPort
    private String pemKey
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private AppUser appUser
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private Scene scene
    private String sceneSecurityId


    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
    }

    Scene getScene() {
        return scene
    }

    void setScene(Scene scene) {
        this.scene = scene
    }

    String getSshUsername() {
        return sshUsername
    }

    void setSshUsername(String sshUsername) {
        this.sshUsername = sshUsername
    }

    String getSshPassword() {
        return sshPassword
    }

    void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword
    }

    String getSshPort() {
        return sshPort
    }

    void setSshPort(String sshPort) {
        this.sshPort = sshPort
    }

    String getPemKey() {
        return pemKey
    }

    void setPemKey(String pemKey) {
        this.pemKey = pemKey
    }
}
