package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.v3.modules.device.model.COAPDevice
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.model.MQTTDevice

/**
 * @author wwhai* @date 2019/6/29 23:14
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class HTTPDeviceForm extends HTTPDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class COAPDeviceForm extends COAPDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class MQTTDeviceForm extends MQTTDevice {
    private String sceneSecurityId

    String getSceneSecurityId() {
        return sceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        this.sceneSecurityId = sceneSecurityId
    }
}

class TerminalHostDeviceForm extends AbstractDevice {

}