package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.DeviceType
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.model.TerminalHostDevice

/**
 * @author wwhai* @date 2019/6/29 23:14
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class HTTPDeviceForm extends HTTPDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class COAPDeviceForm extends COAPDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class MQTTDeviceForm extends MQTTDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class TerminalHostDeviceForm   {
    private String name
    private String sceneSecurityId
    private String sshUsername
    private String sshPassword
    private String sshPort
    private String pemKey
    private String info
    private DeviceType deviceType

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

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
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

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}