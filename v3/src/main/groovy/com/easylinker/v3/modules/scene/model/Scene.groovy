package com.easylinker.v3.modules.scene.model

import com.easylinker.framework.common.model.AbstractModel
import com.easylinker.framework.modules.user.model.AppUser
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class Scene extends AbstractModel {
    private String name
    private String info
    @Enumerated(EnumType.STRING)
    private SceneType sceneType
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private AppUser appUser

    SceneType getSceneType() {
        return sceneType
    }

    void setSceneType(SceneType sceneType) {
        this.sceneType = sceneType
    }

    AppUser getAppUser() {
        return appUser
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser
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


/**
 * 场景的类型
 */
enum SceneType {
    //用户自定义类型的，设备的种类不限制
    CUSTOM,
    //预设模板，比如常见的：温湿度监控模板，开关模板，聊天室模板，显示屏模板
    PRE_INSTALL_TEMPLATE

}
/**
 * 备注：预设的场景类型,这里后面会动态增加
 * `温湿度监控系统
 * `GPS跟踪系统
 * `通用开关系统
 * `串口屏显示系统

 */
enum PreInstallTemplate {
    NOTHING,
    HUMIDITY_TEMPERATURE_TEMPLATE,
    GPS_TEMPLATE,
    GENERAL_SWITCH_TEMPLATE,
    SERIAL_DISPLAY_TEMPLATE
}
