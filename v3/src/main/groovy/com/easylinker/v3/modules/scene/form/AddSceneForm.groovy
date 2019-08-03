package com.easylinker.v3.modules.scene.form


import com.easylinker.v3.common.model.DeviceProtocol
import com.easylinker.v3.modules.scene.model.PreInstallTemplate
import com.easylinker.v3.modules.scene.model.SceneType

class AddSceneForm {
    private String name
    private String info
    private SceneType sceneType
    private DeviceProtocol deviceProtocol
    private PreInstallTemplate preInstallTemplate

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
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

    SceneType getSceneType() {
        return sceneType
    }

    void setSceneType(SceneType sceneType) {
        this.sceneType = sceneType
    }

    PreInstallTemplate getPreInstallTemplate() {
        return preInstallTemplate
    }

    void setPreInstallTemplate(PreInstallTemplate preInstallTemplate) {
        this.preInstallTemplate = preInstallTemplate
    }
}
