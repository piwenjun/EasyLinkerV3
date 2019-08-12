package com.easylinker.mqttservice.controller

import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 设备上下线控制器
 */
@RestController
@RequestMapping("/onOffLine")
class DeviceOnOrOffLineController {
    /**
     * 上线监控
     * @param map
     * @return
     */
    @PostMapping("/on")
    R on(@RequestBody Map<String, Object> map) {

        println map.toMapString()
        return R.ok()
    }
    /**
     * 下线监控
     * @param map
     * @return
     */

    @PostMapping("/off")
    R off(@RequestBody Map<String, Object> map) {

        println map.toMapString()
        return R.ok()
    }
}
