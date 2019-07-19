package com.easylinker.v3.modules.scene.form

class UpdateSceneForm {
    private String name
    private String info
    private String securityId

    String getSecurityId() {
        return securityId
    }

    void setSecurityId(String securityId) {
        this.securityId = securityId
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
