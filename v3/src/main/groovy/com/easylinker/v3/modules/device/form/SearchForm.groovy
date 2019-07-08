package com.easylinker.v3.modules.device.form


import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType

/**
 * mqtt设备搜索条件
 * username
 * clientid
 * info
 */

class SearchMqttForm {
    private String name
    private String info
    private DeviceProtocol deviceProtocol
    private DeviceType deviceType
    private String username
    private String clientId
    private boolean online

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

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
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
}


class SearchCoapForm {
    private String name
    private String info

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

}


class SearchHttpForm {
    private String name
    private String info

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
}
