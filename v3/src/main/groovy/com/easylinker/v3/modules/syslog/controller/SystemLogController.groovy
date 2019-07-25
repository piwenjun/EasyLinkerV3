package com.easylinker.v3.modules.syslog.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.syslog.service.SystemLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    @Autowired
    SystemLogService systemLogService

    SystemLogController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @GetMapping("/listByUser")
    R listByUser(@RequestParam(required = true) int page,
                 @RequestParam(required = true) int size) {

        return R.okWithData(systemLogService.list(getCurrentUser().securityId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))))
    }
}
