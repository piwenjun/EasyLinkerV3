package com.easylinker.v3.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice

import javax.persistence.Entity

/**
 * 终端类型的设备
 */
@Entity
class TerminalHostDevice extends AbstractDevice{
    private String sshUsername
    private String sshPassword
    private String sshPort
    private String pemKey

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
