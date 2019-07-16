package com.easylinker.framework.common.model

import lombok.Data

import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.MappedSuperclass

/**
 * 通用设备抽象，一切设备基于该类
 * @author wwhai* @date 2019/6/28 22:12
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */

@MappedSuperclass
@Data
class AbstractDevice extends AbstractModel {

    private String name
    private String info
    @Enumerated(EnumType.STRING)
    private DeviceProtocol deviceProtocol
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType

    private Date lastActive

    Date getLastActive() {
        return lastActive
    }

    void setLastActive(Date lastActive) {
        this.lastActive = lastActive
    }

    DeviceType getDeviceType() {
        return deviceType
    }

    void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType
    }

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
    }

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
/**
 * 协议类型枚举
 */
enum DeviceProtocol {
    HTTP("HTTP协议设备"),
    CoAP("CoAP协议设备"),
    MQTT("MQTT协议设备"),
    UDP("UDP协议设备"),
    TCP("TCP协议设备"),
    PLACEHOLDER("暂不选择协议")
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
    BOOLEAN("开关型设备"),
    FILE("文件型设备"),
    STREAM("流媒体型设备"),
    TERMINAL_HOST("终端型设备"),
    PLACEHOLDER("暂不选择类型")
    String name

    DeviceType(String name) {
        this.name = name
    }
}
