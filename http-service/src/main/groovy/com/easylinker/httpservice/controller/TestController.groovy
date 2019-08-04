package com.easylinker.httpservice.controller

import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 测试
 * @author wwhai* @date 2019/8/4 13:15
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
class TestController {
    @GetMapping("/")
    R i() {
        return R.ok("测试成功")
    }
}
