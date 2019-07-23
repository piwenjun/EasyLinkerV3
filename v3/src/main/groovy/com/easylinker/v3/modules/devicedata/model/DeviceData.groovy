package com.easylinker.v3.modules.devicedata.model

import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.common.model.DeviceType

import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.MappedSuperclass

/**
 * 设备数据抽象
 */
@MappedSuperclass
class DeviceData extends AbstractModel {
    /**
     * 数据
     */
    private String data
    /**
     * 单位
     */
    private String unit
    /**
     * 设备ID
     */
    private String deviceSecurityId
    /**
     * 备注信息
     */
    private String info
    /**
     * 数据类型（检索的时候用）
     */
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType

    String getData() {
        return data
    }

    void setData(String data) {
        this.data = data
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

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

}