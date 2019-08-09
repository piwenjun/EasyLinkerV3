package com.easylinker.v3.modules.product.form

/**
 * @author wwhai* @date 2019/8/9 23:55
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class QueryForm {
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
