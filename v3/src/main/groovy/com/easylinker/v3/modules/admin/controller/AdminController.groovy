package com.easylinker.v3.modules.admin.controller

import com.easylinker.v3.common.controller.AbstractController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 后台管理员
 * @author wwhai* @date 2019/8/11 23:13
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/admin")
class AdminController extends AbstractController {
    AdminController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
