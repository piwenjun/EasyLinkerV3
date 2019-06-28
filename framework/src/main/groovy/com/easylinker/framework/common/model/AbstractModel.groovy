package com.easylinker.framework.common.model

class AbstractModel implements Serializable {

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

}
