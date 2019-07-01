package com.easylinker.v3.modules.test

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.web.R
import org.apache.shiro.authz.annotation.RequiresRoles
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
//        try {
//            1 / 0
//        } catch (Exception e) {
//            throw new XException(100, "被除数不能0")
//        }
        R.okWithData("测试成功:" + new Date())
    }

    @RequestMapping("/testRole1")
    @RequiresRoles("BASE_ROLE")
    R testRole1() {
        println(getCurrentUser().id)

        R.okWithData("测试成功:" + new Date())
    }

    @RequestMapping("/testRole2")
    R testRole2() {

        R.okWithData("测试成功:" + new Date())
    }
}
