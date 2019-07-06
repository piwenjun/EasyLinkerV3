package com.easylinker.v3.modules.device.form

import com.easylinker.framework.common.model.DeviceProtocol

/**
 * @author wwhai* @date 2019/7/6 19:09
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class ListDeviceForm {
    private DeviceProtocol deviceProtocol
    private String SceneSecurityId

    DeviceProtocol getDeviceProtocol() {
        return deviceProtocol
    }

    void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol
    }

    String getSceneSecurityId() {
        return SceneSecurityId
    }

    void setSceneSecurityId(String sceneSecurityId) {
        SceneSecurityId = sceneSecurityId
    }
}
