package com.easylinker.v3.modules.device.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * @author wwhai* @date 2019/6/29 23:00
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/device")
@RequiresAuthentication
class DeviceController extends AbstractController {
    DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @RequestMapping("/addHttp")
    R addHTTP() {
        R.ok()

    }

    @RequestMapping("/addCoap")
    R addCOAP() {
        R.ok()

    }

    @RequestMapping("/addMqtt")
    R addMQTT() {
        R.ok()

    }
    /**
     * 协议类型枚举
     */
    enum DeviceProtocol {
        HTTP, COAP, MQTT, UDP, TCP
    }
}
