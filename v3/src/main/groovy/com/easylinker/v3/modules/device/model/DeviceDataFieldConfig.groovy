package com.easylinker.v3.modules.device.model

import com.easylinker.v3.common.model.AbstractModel

import javax.persistence.Entity

/**
 * 设备的数据字段名列表:是个数组:[CO2,TMP,HUM.....]
 */
@Entity
class DeviceDataFieldConfig extends AbstractModel {
    private String deviceSecurityId
    private String fields

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

    String getFields() {
        return fields
    }

    void setFields(String fields) {
        this.fields = fields
    }
}
