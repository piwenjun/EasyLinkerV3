package com.easylinker.v3.modules.scene.model


import com.easylinker.v3.common.model.AbstractModel
import com.easylinker.v3.modules.user.model.AppUser
import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

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
    CUSTOM("自定义模板")
    /**
     * 7-26日更新：之前联系的两家公司 没有提供模块开发文档
     * 当时答应开发串口屏  GPS  还有个开发板的SDK对接 但是最近他们比较忙没做
     * 所以模板暂时取消 等后面有开发板以后再加
     * @param name
     */
    //预设模板，比如常见的：温湿度监控模板，开关模板，聊天室模板，显示屏模板
    //PRE_INSTALL_TEMPLATE("系统预装模板")

    SceneType(String name) {
        this.name = name
    }
    String name

    @Override
    String toString() {
        return name()
    }
}
/**
 * 备注：预设的场景类型,这里后面会动态增加
 * `温湿度监控系统
 * `GPS跟踪系统
 * `通用开关系统
 * `串口屏显示系统

 */
enum PreInstallTemplate {
    PLACEHOLDER("暂不选择"),
    HUMIDITY_TEMPERATURE_TEMPLATE("温湿度模板"),
    GPS_TEMPLATE("GPS定位模板"),
    GENERAL_SWITCH_TEMPLATE("通用开关模板"),
    SERIAL_DISPLAY_TEMPLATE("串口显示屏模板")

    String name

    PreInstallTemplate(String name) {
        this.name = name

    }

    @Override
    String toString() {
        return name()
    }
}
