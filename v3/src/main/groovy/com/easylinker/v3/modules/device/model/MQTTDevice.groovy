package com.easylinker.v3.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice

import javax.persistence.Entity

/**
 * @author wwhai* @date 2019/6/29 22:58
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
class MQTTDevice extends AbstractDevice{
    private String username
    private String password
    private String topic

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getTopic() {
        return topic
    }

    void setTopic(String topic) {
        this.topic = topic
    }
}
