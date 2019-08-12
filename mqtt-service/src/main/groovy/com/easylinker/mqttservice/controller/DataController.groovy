package com.easylinker.mqttservice.controller

import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mqttData")
class DataController {
    @PostMapping("/post")
    R post(@RequestBody Map<String, Object> map) {
        println map.toMapString()
        return R.ok()
    }

}
