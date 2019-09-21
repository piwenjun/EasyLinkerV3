package com.easylinker.v3.modules.product.form




class UpdateProductForm {

    private String securityId
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

    String getSecurityId() {
        return securityId
    }

    void setSecurityId(String securityId) {
        this.securityId = securityId
    }

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
}
