package com.easylinker.v3.modules.devicedata.form


import com.easylinker.framework.common.model.DeviceType

/**
 * 辅助查询
 */
class DataQueryForm {

    private String deviceSecurityId
    private DeviceType deviceType

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }
}
