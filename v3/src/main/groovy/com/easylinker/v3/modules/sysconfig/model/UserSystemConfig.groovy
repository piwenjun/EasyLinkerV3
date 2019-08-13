package com.easylinker.v3.modules.sysconfig.model


import com.alibaba.fastjson.JSONObject
import com.easylinker.v3.common.model.AbstractModel

import javax.persistence.Entity

/**
 * @author wwhai* @date 2019/8/10 22:37
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
//用户系统配置
@Entity
class UserSystemConfig extends AbstractModel {
    /**
     * 这里可以控制显示设备页面的Tabs,格式如下
     *{*     "data": [
     *{*             "name": "HTTP协议设备",
     *             "key": "HTTP"
     *}*     ]
     *}*/

    private JSONObject displayTabs
    /**
     * 用户ID
     */
    private String userSecurityId

    String getDisplayTabs() {
        return displayTabs
    }

    void setDisplayTabs(JSONObject displayTabs) {
        this.displayTabs = displayTabs
    }

    String getUserSecurityId() {
        return userSecurityId
    }

    void setUserSecurityId(String userSecurityId) {
        this.userSecurityId = userSecurityId
    }
}
