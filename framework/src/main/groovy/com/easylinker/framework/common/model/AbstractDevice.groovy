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
    HTTP, COAP, MQTT, UDP, TCP, PLACEHOLDER
}
/**
 * 设备类型
 */
enum DeviceType {
    VALUE, TEXT, BOOLEAN, FILE,
}
