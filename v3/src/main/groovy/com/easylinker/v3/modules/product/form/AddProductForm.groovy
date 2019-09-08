package com.easylinker.v3.modules.product.form

import javax.validation.constraints.NotBlank


class AddProductForm {
    /**
     * 厂家
     */
    private String factory
    /**
     * 型号
     */
    private String model
    /**
     * 名称
     */
    private String name
    /**
     * 备注
     */
    private String info

    /**
     * 场景id
     */
    @NotBlank(message = "场景不能为空")
    private String sceneSecurityId;

    /**
     * 创建时间
     */
    private Date createTime;


    String getFactory() {
        return factory
    }

    void setFactory(String factory) {
        this.factory = factory
    }

    String getModel() {
        return model
    }

    void setModel(String model) {
        this.model = model
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

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }

}
