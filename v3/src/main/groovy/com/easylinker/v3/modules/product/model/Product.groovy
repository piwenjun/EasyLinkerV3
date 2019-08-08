package com.easylinker.v3.modules.product.model


import com.easylinker.v3.common.model.AbstractModel

import javax.persistence.Entity

/**
 * 2019-07-24更新 全新的功能：
 * 方案的主要涉及思路，先抽象设备，设备仅仅是个分类依据，
 * 然后设备下面可以挂模块，想一下一个设备可能有温度湿度和视频监控
 * 重新定义为：产品设备
 */
@Entity
class Product extends AbstractModel {
    private String name
    private String info

    /**
     * 最后活跃时间
     */
    private Date lastActive
    /**
     * 设备sceneSecurityId
     */

    private String sceneSecurityId
    /**
     * 用户ID
     */
    private String appUserSecurityId

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


    Date getLastActive() {
        return lastActive
    }

    void setLastActive(Date lastActive) {
        this.lastActive = lastActive
    }

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }

    String getAppUserSecurityId() {
        return appUserSecurityId
    }

    void setAppUserSecurityId(String appUserSecurityId) {
        this.appUserSecurityId = appUserSecurityId
    }
}
/**
 * 产品的属性：
 * 这个概念用在哪里？设想这个场景：用户开发了一个黑匣子包含了温、湿度、CO2、光等数据采集；
 * 参数最终可以计算出一个指标：假设为环境指数，这个指数是【产品】属性而非【设备】属性
 * +--------------------------------+
 * |                                |
 * |              电 压 ：20V        |
 * |              电 流 ：2A         |
 * |    设 备      属 性 ：A1         |
 * |              属 性 ：A2         |
 * |                                |
 * +--------------------------------+
 */
@Entity
class ProductProperty extends AbstractModel{

    /**
     * 产品
     */
    private String productSecurityId
    /**
     * 属性：是一个String的数组，格式如下
     * p=[p1,p2,p3......]
     * 假设是上面个讲的那个黑匣子：包含两个指标[环境参数,运行参数]
     * p=[ENV,RUN]
     * 同时后端会提供一个接口，通过这两个属性去拿产品的当前属性数据
     * 这个数据来自产品自身上报，和设备无关
     */
    private String property

    String getProductSecurityId() {
        return productSecurityId
    }

    void setProductSecurityId(String productSecurityId) {
        this.productSecurityId = productSecurityId
    }

    String getProperty() {
        return property
    }

    void setProperty(String property) {
        this.property = property
    }
}