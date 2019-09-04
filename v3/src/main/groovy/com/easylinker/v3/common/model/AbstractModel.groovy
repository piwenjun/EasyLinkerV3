package com.easylinker.v3.common.model

import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //基本信息
    Long id
    /**
     * 安全ID，对外如果用到数据库id的时候用词ID代替
     * 主要是为了考虑被人猜测破解规律
     */
    private String securityId = UUID.randomUUID().toString().replace("-", "")
    /**
     * 项目创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd:HH:MM:ss")

    private Date createTime = new Date()

    /**
     * 是否软删除
     */
    private boolean isDelete = false

    /**
     * 项目更新时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")

    private Date updateTime = new Date()

    Long getId() {
        return id
    }

    void setId(Long id) {
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

    boolean getIsDelete() {
        return isDelete
    }

    void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete
    }
}
