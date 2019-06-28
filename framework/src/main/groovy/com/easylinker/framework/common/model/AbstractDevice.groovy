package com.easylinker.framework.common.model

import lombok.Data

import javax.persistence.Column
import javax.persistence.MappedSuperclass

/**
 * 通用设备抽象，一切设备基于该类
 * @author wwhai* @date 2019/6/28 22:12
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */

@MappedSuperclass
@Data
class AbstractDevice extends AbstractModel {


    private String name
    private String info
    private String type

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

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }
}
