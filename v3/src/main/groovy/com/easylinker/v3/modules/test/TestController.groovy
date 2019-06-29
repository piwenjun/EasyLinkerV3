package com.easylinker.v3.modules.test

import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.web.R
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresRoles
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by admin on 2019/6/10 8:55
 *
 */

@RestController
class TestController {
    @RequestMapping("/test")
    R test() {
        try {
            1 / 0
        } catch (Exception e) {
            throw new XException(100, "被除数不能0")
        }
        R.okWithData("测试成功:" + new Date())
    }

    @RequestMapping("/testRole1")
    @RequiresAuthentication
    @RequiresRoles("ADMIN")
    R testRole1() {

        R.okWithData("测试成功:" + new Date())
    }

    @RequiresRoles("USER")
    @RequestMapping("/testRole2")
    R testRole2() {

        R.okWithData("测试成功:" + new Date())
    }
}
