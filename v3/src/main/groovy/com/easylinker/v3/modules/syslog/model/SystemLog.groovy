package com.easylinker.v3.modules.syslog.model

import com.alibaba.fastjson.annotation.JSONField

class SystemLog {
    private String reason
    private String info
    private String userSecurityId

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date()

    String getReason() {
        return reason
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }

    void setReason(String reason) {
        this.reason = reason
    }

    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }
}
