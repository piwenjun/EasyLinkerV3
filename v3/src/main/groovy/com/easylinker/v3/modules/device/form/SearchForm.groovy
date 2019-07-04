package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.AbstractDevice
import lombok.Data

/**
 * mqtt设备搜索条件
 * username
 * clientid
 * info
 */
@Data
class SearchMqttForm extends AbstractDevice {

    private String username
    private String clientId
    private boolean online


}

@Data
class SearchCoapForm extends AbstractDevice {


}

@Data
class SearchHttpForm extends AbstractDevice {


}
