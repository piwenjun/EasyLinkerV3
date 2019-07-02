package com.easylinker.v3.modules.scene.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/")
    R index() {
        return R.ok(1, "场景管理")
    }
}
