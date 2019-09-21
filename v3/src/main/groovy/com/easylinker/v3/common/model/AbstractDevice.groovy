package com.easylinker.v3.common.model

import com.easylinker.framework.utils.SerialNumberUtils
import com.vladmihalcea.hibernate.type.json.JsonStringType
import lombok.Data
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

import javax.persistence.Column
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
@TypeDef(name = "json", typeClass = JsonStringType.class)
class AbstractDevice extends AbstractModel {
    private String token
    private String name
    private String info
    private String sn = SerialNumberUtils.getSerialNumber()
    /**
     * 设备的数据字段配置
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> dataFields = ["value"]

    /**
     * 设备的运行状态参数
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> deviceParam = ["online"]

    List<String> getDeviceParam() {
        return deviceParam
    }

    void setDeviceParam(List<String> deviceParam) {
        this.deviceParam = deviceParam
    }
    /**
     * 设备协议：为了生成SDK和终端交互
     */
    @Enumerated(EnumType.STRING)
    private DeviceProtocol deviceProtocol
    /**
     * 设备类型：用在前端显示界面
     */
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType
    /**
     * 最后活跃时间
     */
    private Date lastActive
    /**
     * 设备状态
     */
    @Enumerated(EnumType.STRING)
    DeviceStatus deviceStatus = DeviceStatus.OFFLINE
    private String sceneSecurityId

    /**
     * 产品的ID
     */
    private String productSecurityId

    String getProductSecurityId() {
        return productSecurityId
    }

    void setProductSecurityId(String productSecurityId) {
        this.productSecurityId = productSecurityId
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    String getSn() {
        return sn
    }

    void setSn(String sn) {
        this.sn = sn
    }

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

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    DeviceStatus getDeviceStatus() {
        return deviceStatus
    }

    void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus
    }

    List<String> getDataFields() {
        return dataFields
    }

    void setDataFields(List<String> dataFields) {
        this.dataFields = dataFields
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
    TCP("TCP协议设备")
   // PLACEHOLDER("暂不选择协议")
    String name

    DeviceProtocol(String name) {
        this.name = name
    }
}
/**
 * 设备类型：
 * [8-4号备注]：这里的设备类型主要用途：
 * 1 数据摘选查找，比如X类型的设备的数据一定是X类型，方便统一数据
 * 2 后期的设备模板可以使用
 */
enum DeviceType {
    VALUE("数值型设备"),
    TEXT("文本型设备"),
    BOOLEAN("布尔型设备"),
    SWITCH("开关型设备"),
    FILE("文件型设备")
//    STREAM("流媒体型设备"),
//    TERMINAL_HOST("终端型设备"),
    //PLACEHOLDER("暂不选择类型")
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