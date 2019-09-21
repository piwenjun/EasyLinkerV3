package com.easylinker.v3.modules.sysconfig.model

import com.alibaba.fastjson.JSONArray
import com.easylinker.v3.common.model.AbstractModel
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

import javax.persistence.Column
import javax.persistence.Entity

/**
 * @author wwhai* @date 2019/8/10 22:37
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)

class UserSystemConfig extends AbstractModel {

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JSONArray displayTabs
    /**
     * 用户ID
     */
    private String userSecurityId

    JSONArray getDisplayTabs() {
        return displayTabs
    }

    void setDisplayTabs(JSONArray displayTabs) {
        this.displayTabs = displayTabs
    }

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }
}
