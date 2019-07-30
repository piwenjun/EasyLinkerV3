package com.easylinker.v3.modules.devicemodule.model

import com.easylinker.framework.utils.SerialNumberUtils
import com.easylinker.v3.modules.model.AbstractDevice
import com.easylinker.v3.modules.model.DeviceProtocol

import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * 2019-07-24更新 全新的功能：
 * 方案的主要涉及思路，先抽象设备，设备仅仅是个分类依据，
 * 然后设备下面可以挂模块，想一下一个设备可能有温度湿度和视频监控
 */
//@Entity
class Device extends AbstractDevice {
    private String name
    private String info
    private String sn = SerialNumberUtils.getSerialNumber()
    /**
     * 设备协议：为了生成SDK和终端交互
     */
    @Enumerated(EnumType.STRING)
    private DeviceProtocol deviceProtocol
    /**
     * 最后活跃时间
     */
    private Date lastActive
    /**
     * 设备状态
     */

    private String sceneSecurityId
    /**
     * 用户ID
     */
    private String appUserSecurityId

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

    String getSn() {
        return sn
    }

    void setSn(String sn) {
        this.sn = sn
    }

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
    }

    Date getLastActive() {
        return lastActive
    }

    void setLastActive(Date lastActive) {
        this.lastActive = lastActive
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    String getAppUserSecurityId() {
        return appUserSecurityId
    }

    void setAppUserSecurityId(String appUserSecurityId) {
        this.appUserSecurityId = appUserSecurityId
    }
}
