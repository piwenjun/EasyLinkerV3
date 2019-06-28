package com.easylinker.framework.modules.device.model

import com.easylinker.framework.common.model.AbstractDevice
import lombok.Data

import javax.persistence.Entity

/**
 * 终端设备
 * 0.1版本 只支持Linux
 * 后面考虑支持windows和VNC
 */
@Entity
@Data
class Device extends AbstractDevice {

}
