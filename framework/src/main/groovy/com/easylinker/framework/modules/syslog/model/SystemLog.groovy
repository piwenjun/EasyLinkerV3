package com.easylinker.framework.modules.syslog.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.Entity

@Entity
class SystemLog extends AbstractModel{
    private String reason
    private String info
    private String userSecurityId

    String getReason() {
        return reason
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
