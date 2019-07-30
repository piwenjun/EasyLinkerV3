package com.easylinker.v3.modules.device.form


import com.easylinker.v3.modules.model.DeviceType

/**
 * @author wwhai* @date 2019/6/29 23:14
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class HTTPDeviceForm {
    private String info
    private String name

    private String sceneSecurityId
    private DeviceType deviceType
    private String token

    String getSceneSecurityId() {
        return sceneSecurityId
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

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }
}
/**
 *
 */
class CoAPDeviceForm {
    private String sceneSecurityId
    private DeviceType deviceType
    private String token
    private String info
    private String name

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

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

/**
 *
 */
class TCPDeviceForm {
    private String sceneSecurityId
    private DeviceType deviceType
    private String username
    private String password
    private String token
    private String ip
    private String info
    private String name

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

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
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

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    String getIp() {
        return ip
    }

    void setIp(String ip) {
        this.ip = ip
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}
/**
 *
 */
class UDPDeviceForm {
    private String sceneSecurityId
    private String token
    private String info
    private String name

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
    private DeviceType deviceType

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

/**
 *
 */
class MQTTDeviceForm {
    private DeviceType deviceType
    private String sceneSecurityId
    private String username
    private String info
    private String name

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
    private String password

    private String clientId

    private boolean online

    private boolean isSuperUser

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
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

    String getClientId() {
        return clientId
    }

    void setClientId(String clientId) {
        this.clientId = clientId
    }

    boolean getOnline() {
        return online
    }

    void setOnline(boolean online) {
        this.online = online
    }

    boolean getIsSuperUser() {
        return isSuperUser
    }

    void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser
    }
}
/**
 *
 */
class TerminalHostDeviceForm {
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