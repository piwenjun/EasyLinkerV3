package com.easylinker.v3.modules.device.form

class DeviceFieldConfigForm {
    private String deviceSecurityId
    private List<Map<String, Object>> fields

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

    List<Map<String, Object>> getFields() {
        return fields
    }

    void setFields(List<Map<String, Object>> fields) {
        this.fields = fields
    }
}
