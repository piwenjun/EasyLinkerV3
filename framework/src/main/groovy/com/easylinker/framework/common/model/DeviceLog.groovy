package com.easylinker.framework.common.model

/**
 * 设备日志表，存放进MongoDb的
 * @author wwhai* @date 2019/8/7 21:11
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class DeviceLog {
    /**
     * 设备
     */
    private String deviceSecurityId
    /**
     * 备注信息
     */
    private String info
    /**
     * 事件
     */
    private String event

    /**
     * 时间
     */
    private Date createTime

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    String getEvent() {
        return event
    }

    void setEvent(String event) {
        this.event = event
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }
}
