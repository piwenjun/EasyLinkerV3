package com.easylinker.framework.modules.user.controller

import com.easylinker.framework.common.controller.AbstractController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController extends AbstractController {
    UserController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
