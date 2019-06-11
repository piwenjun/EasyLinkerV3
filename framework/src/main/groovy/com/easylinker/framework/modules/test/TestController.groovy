package com.easylinker.framework.modules.test

import com.easylinker.framework.common.web.R
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
        R.ok("测试成功:" + new Date())
    }
}
