package com.easylinker.v3.modules.device.controller

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.model.DeviceProtocol
import com.easylinker.framework.common.model.DeviceType
import com.easylinker.framework.common.web.R
import com.easylinker.v3.modules.device.form.HTTPDeviceForm
import com.easylinker.v3.modules.device.model.HTTPDevice
import com.easylinker.v3.modules.device.service.DeviceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * @author wwhai* @date 2019/6/29 23:00
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/device")
//@RequiresAuthentication
class DeviceController extends AbstractController {
    @Autowired
    DeviceService deviceService

    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @PostMapping("/addHttp")
    R addHTTP(@RequestBody @Valid HTTPDeviceForm httpDeviceForm) {
        HTTPDevice httpDevice = new HTTPDevice(name: httpDeviceForm.name,
                info: httpDeviceForm.info,
                deviceType: httpDeviceForm.deviceType, deviceProtocol: DeviceProtocol.HTTP)
        deviceService.add(httpDevice, DeviceProtocol.HTTP)
        R.ok()

    }

    @PostMapping("/addCoap")
    R addCOAP() {
        R.ok()

    }

    @PostMapping("/addMqtt")
    R addMQTT() {
        R.ok()

    }

    static void main(String[] args) {
        println(JSONObject.toJSONString(new HTTPDevice(name: "树莓派",
                info: "测试用的数据",
                deviceType: DeviceType.VALUE,
                deviceProtocol: DeviceProtocol.HTTP)))
    }
}
