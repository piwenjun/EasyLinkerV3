package com.easylinker.v3.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice

import javax.persistence.Entity

/**
 * @author wwhai* @date 2019/6/29 22:57
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
class HTTPDevice extends AbstractDevice{
    private String token

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }
}
