package com.easylinker.mqttservice.controller

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 设备上报日志
 */
@RestController
@RequestMapping("/status")
class DeviceStatusController {


    /**
     *
     * @param map
     * @return
     */
    @PostMapping("/postStatus")
    R postStatus(@RequestBody Map<String, Object> map) {
        println JSONObject.toJSONString(map)

        return R.ok()
    }

}
