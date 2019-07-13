package com.easylinker.v3.modules.syslog.controller

import com.easylinker.framework.common.controller.AbstractController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 系统日志
 * @author wwhai* @date 2019/7/13 10:36
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/syslog")
class SystemLogController extends AbstractController {
    SystemLogController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
