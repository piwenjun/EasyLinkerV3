package com.easylinker.framework.modules.device.model

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by admin on 2019/6/10 14:21
 *
 */

/**
 * 终端设备类型
 */
class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //基本信息
    Long id
    private String name
    private String info

    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

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
