package com.easylinker.v3.modules.devicemodule.model


import com.easylinker.v3.modules.model.AbstractModel

import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * 模块的外挂组件【模块】
 */
class DeviceModule extends AbstractModel {
    private String deviceSecurityId

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }
}

class CoAPModule extends DeviceModule {

    @Enumerated(EnumType.STRING)
    private ModuleType moduleType = ModuleType.BOOLEAN
    @Enumerated(EnumType.STRING)
    private ModuleProtocol moduleProtocol
}


/**
 * 模块类型
 */
enum ModuleType {
    VALUE("数值型模块"),
    TEXT("文本型模块"),
    BOOLEAN("布尔型模块"),
    SWITCH("开关型模块"),
    FILE("文件型模块"),
    STREAM("流媒体型模块"),
    TERMINAL_HOST("终端型模块"),
    PLACEHOLDER("暂不选择类型")
    String name

    ModuleType(String name) {
        this.name = name
    }
}

/**
 * 协议类型枚举
 */
enum ModuleProtocol {
    HTTP("HTTP协议"),
    CoAP("CoAP协议"),
    MQTT("MQTT协议"),
    UDP("UDP协议"),
    TCP("TCP协议"),
    PLACEHOLDER("暂不选择")
    String name

    ModuleProtocol(String name) {
        this.name = name
    }
}