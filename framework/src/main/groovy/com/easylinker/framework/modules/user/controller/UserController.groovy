package com.easylinker.framework.modules.user.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/user")
class UserController extends AbstractController {
    @Override
    R getById(int id) {
        return null
    }

    @Override
    R deleteById(long id) {

        R.ok()
    }

    @Override
    R list(int page, int size) {
        return null
    }

    UserController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
