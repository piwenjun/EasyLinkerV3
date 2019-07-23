package com.easylinker.v3.modules.test

import com.easylinker.framework.common.config.security.RequireAuthRoles
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.AbstractDevice
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.device.model.MQTTDevice
import com.easylinker.v3.modules.device.service.DeviceService
import com.easylinker.v3.modules.devicedata.model.DeviceData
import com.easylinker.v3.modules.devicedata.service.DeviceDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * Created by admin on 2019/6/10 8:55
 *
 */

@RestController
class TestController extends AbstractController {

    TestController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @RequestMapping("/test")
    R test() {
        R.okWithData("测试成功:" + new Date())
    }

    @RequireAuthRoles(roles = ['ADMIN', 'BASE_ROLE'])
    @RequestMapping("/testRole1")
    R testRole1() {
        println(getCurrentUser().id)

        R.okWithData(['ADMIN', 'BASE_ROLE'])
    }

    @RequestMapping("/testRole2")
    R testRole2() {

        R.okWithData("公共接口")
    }


    /**
     * 测试设备上传数据
     * @return
     */
    @Autowired
    DeviceService deviceService

    @Autowired
    DeviceDataService deviceDataService

    @PostMapping("/testPush")
    R testPush(@RequestBody PushForm pushForm) {

        AbstractDevice abstractDevice = deviceService.detail(pushForm.deviceSecurityId, pushForm.deviceProtocol)

        if (abstractDevice) {
            switch (pushForm.deviceProtocol) {
                case DeviceProtocol.MQTT:
                    MQTTDevice device = abstractDevice as MQTTDevice
                    if (device.deviceType == DeviceType.BOOLEAN) {
                        DeviceData data = new DeviceData()
                        data.setInfo("测试开关数据")
                        data.setDeviceSecurityId(device.securityId)
                        data.setData(pushForm.data.toString())
                        deviceDataService.save(data)
                        return R.ok("数据提交成功")
                    } else {
                        return R.ok("测试数据")

                    }

                default: return R.ok("测试数据")

            }
        }

        return R.ok("测试数据")

    }

}

class PushForm {
    String deviceSecurityId
    DeviceProtocol deviceProtocol
    List<Boolean> data

}