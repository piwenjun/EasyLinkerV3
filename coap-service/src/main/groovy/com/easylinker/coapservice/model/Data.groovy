package com.easylinker.coapservice.model
/**
 * 设备数据
 */
class DeviceData {

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
     * 是否软删除
     */
    private boolean isDelete = false

    /**
     * 项目更新时间
     */
    private Date updateTime

    /**
     * 数据
     */
    private String data
    /**
     * 单位
     */
    private String unit
    /**
     * 设备ID
     */
    private String deviceSecurityId
    /**
     * 备注信息
     */
    private String info
    /**
     *
     * @return
     */
    private String dataType

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

    boolean getIsDelete() {
        return isDelete
    }

    void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete
    }

    Date getUpdateTime() {
        return updateTime
    }

    void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime
    }

    String getUnit() {
        return unit
    }

    void setUnit(String unit) {
        this.unit = unit
    }

    String getDataType() {
        return dataType
    }

    void setDataType(String dataType) {
        this.dataType = dataType
    }

    String getData() {
        return data
    }

    void setData(String data) {
        this.data = data
    }


    String getInfo() {
        return info
    }

    void setInfo(String info) {
        this.info = info
    }

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }

}