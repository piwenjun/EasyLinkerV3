package com.easylinker.v3.modules.devicedata.model

import com.easylinker.framework.common.model.AbstractModel

import javax.persistence.Entity

/**
 * 设备数据抽象
 */
@Entity
class DeviceData extends AbstractModel {
    private String deviceSecurityId

    String getDeviceSecurityId() {
        return deviceSecurityId
    }

    void setDeviceSecurityId(String deviceSecurityId) {
        this.deviceSecurityId = deviceSecurityId
    }


}
/**
 * 值类数据
 */
@Entity
class ValueData extends DeviceData {
    //数据值
    private float value
    //数据单位
    private String unit

    float getValue() {
        return value
    }

    void setValue(float value) {
        this.value = value
    }

    String getUnit() {
        return unit
    }

    void setUnit(String unit) {
        this.unit = unit
    }
}

/**
 * 文本类数据
 */
@Entity
class TextData extends DeviceData {

    private String text

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }
}
/**
 * 布尔数据
 */
@Entity
class BooleanData extends DeviceData {

    private boolean data

    boolean getData() {
        return data
    }

    void setData(boolean data) {
        this.data = data
    }
}
/**
 * 文件类数据
 */
@Entity
class FileData extends DeviceData {

    private String fileURL

    String getFileURL() {
        return fileURL
    }

    void setFileURL(String fileURL) {
        this.fileURL = fileURL
    }
}