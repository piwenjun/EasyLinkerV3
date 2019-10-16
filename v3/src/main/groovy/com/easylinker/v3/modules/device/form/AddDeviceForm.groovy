package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.DeviceType


/**
 * @author wwhai* @date 2019/6/29 23:14
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class BaseDeviceForm {
    private String token
    /**
     * 设备的数据字段
     */
    private List<String> dataFields
    /**
     * 设备的属性字段
     */
    private List<String> deviceParam

    private String info
    private String name
    private String sceneSecurityId
    private DeviceType deviceType

    List<String> getDeviceParam() {
        return deviceParam
    }

    void setDeviceParam(List<String> deviceParam) {
        this.deviceParam = deviceParam
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    List<String> getDataFields() {
        return dataFields
    }

    void setDataFields(List<String> dataFields) {
        this.dataFields = dataFields
    }

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }
}

class HTTPDeviceForm extends BaseDeviceForm {

}
/**
 *
 */
class CoAPDeviceForm extends BaseDeviceForm {

}

/**
 *
 */
class TCPDeviceForm extends BaseDeviceForm {

    private String username
    private String password
    private String ip

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

    String getIp() {
        return ip
    }

    void setIp(String ip) {
        this.ip = ip
    }
}
/**
 *
 */
class UDPDeviceForm extends BaseDeviceForm {

}

/**
 *
 */
class MQTTDeviceForm extends BaseDeviceForm {

}
/**
 *
 */
class TerminalHostDeviceForm extends BaseDeviceForm {
    private String sceneSecurityId
    private String sshUsername
    private String sshPassword
    private String sshPort = 22
    private String pemKey

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
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