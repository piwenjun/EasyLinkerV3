package com.easylinker.framework.modules.device.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.Entity

/**
 * 协议类型表
 * @author wwhai* @date 2019/6/28 23:10
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Entity
class DeviceProtocol extends AbstractModel {
    private String name
    private String info

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
}
