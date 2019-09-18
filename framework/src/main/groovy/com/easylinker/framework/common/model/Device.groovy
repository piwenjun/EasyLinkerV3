package com.easylinker.framework.common.model


/**
 * 协议类型枚举
 */
enum DeviceProtocol {
    HTTP("HTTP协议设备"),
    CoAP("CoAP协议设备"),
    MQTT("MQTT协议设备"),
    UDP("UDP协议设备"),
    TCP("TCP协议设备")
    //PLACEHOLDER("暂不选择协议")
    String name

    DeviceProtocol(String name) {
        this.name = name
    }
}
/**
 * 设备类型
 */
enum DeviceType {
    VALUE("数值型设备"),
    TEXT("文本型设备"),
    BOOLEAN("布尔型设备"),
    SWITCH("开关型设备"),
    FILE("文件型设备"),
    STREAM("流媒体型设备"),
    TERMINAL_HOST("终端型设备")
    String name

    DeviceType(String name) {
        this.name = name
    }
}
/**
 * 设备基本状态
 */
enum DeviceStatus {
    ONLINE("在线"),
    OFFLINE("离线"),
    RUNNING("运行"),
    ABNORMAL("异常"),
    CRUSH("崩溃"),
    OVER_LOAD("过载"),
    UN_KNOW("未知")
    String title

    DeviceStatus(String title) {
        this.title = title
    }

    @Override
    String toString() {
        return name()
    }
}