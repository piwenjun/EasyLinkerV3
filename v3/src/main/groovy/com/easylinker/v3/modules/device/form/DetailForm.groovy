package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.DeviceProtocol

class DetailForm {
    private String securityId
    private DeviceProtocol deviceProtocol

    String getSecurityId() {
        return securityId
    }

    void setSecurityId(String securityId) {
        this.securityId = securityId
    }

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
    }
}
