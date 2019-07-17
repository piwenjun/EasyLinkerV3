package com.easylinker.v3.modules.test


import com.easylinker.framework.common.config.security.RequireAuthRoles
import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
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






}
