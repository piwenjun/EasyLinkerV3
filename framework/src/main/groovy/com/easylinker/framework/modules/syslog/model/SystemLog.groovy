package com.easylinker.framework.modules.syslog.model

class SystemLog {
    private String reason
    private String info
    private String userSecurityId
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
