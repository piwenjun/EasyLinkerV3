package com.easylinker.framework.common.model

import org.hibernate.annotations.GenericGenerator

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractModel {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    //这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    //基本信息
    String id
    /**
     * 安全ID，对外如果用到数据库id的时候用词ID代替
     * 主要是为了考虑被人猜测破解规律
     */
    private String securityId
    /**
     * 项目创建时间
     */
    private Date createTime
    /**
     * 项目更新时间
     */
    private Date updateTime

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getSecurityId() {
        return securityId
    }

    void setSecurityId(String securityId) {
        this.securityId = securityId
    }

    Date getCreateTime() {
        return createTime
    }

    void setCreateTime(Date createTime) {
        this.createTime = createTime
    }

    Date getUpdateTime() {
        return updateTime
    }

    void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime
    }
}
