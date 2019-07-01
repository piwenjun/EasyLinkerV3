package com.easylinker.v3.modules.scene.controller

import com.easylinker.framework.common.controller.AbstractController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 场景业务
 */
@RestController
@RequestMapping("/scene")
class SceneController extends AbstractController {
    SceneController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
