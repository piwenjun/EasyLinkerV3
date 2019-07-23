package com.easylinker.v3.modules.deviceecho.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.Entity

/**
 * @author wwhai* @date 2019/7/23 22:51
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

/**
 * 设备操作日志:
 * 12:35:25[张三]给【001】号设备发送了【关机】指令
 */
@Entity
class DeviceOperateLog extends AbstractModel {
    /**
     * 发起人
     */
    private String userSecurityId
    /**
     * 设备
     */
    private String deviceSecurityId
    /**
     * 事件
     */
    private String event
    /**
     * 操作
     */
    private String operate
    /**
     * 具体发送的内容
     */
    private String data

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

    String getEvent() {
        return event
    }

    void setEvent(String event) {
        this.event = event
    }

    String getOperate() {
        return operate
    }

    void setOperate(String operate) {
        this.operate = operate
    }

    String getData() {
        return data
    }

    void setData(String data) {
        this.data = data
    }
}
/**
 * 设备操作返回结果
 */
@Entity
class DeviceOperateEcho extends AbstractModel {
    /**
     * 设备
     */
    private String deviceOperateLogSecurityId

    /**
     * 具体回显的内容
     */
    private String data

    String getDeviceOperateLogSecurityId() {
        return deviceOperateLogSecurityId
    }

    void setDeviceOperateLogSecurityId(String deviceOperateLogSecurityId) {
        this.deviceOperateLogSecurityId = deviceOperateLogSecurityId
    }

    String getData() {
        return data
    }

    void setData(String data) {
        this.data = data
    }
}
