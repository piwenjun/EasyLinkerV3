package com.easylinker.v3.modules.schedule.controller


import com.easylinker.v3.common.controller.AbstractController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 定时计划任务
 * @author wwhai* @date 2019/7/13 10:44
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@RestController
@RequestMapping("/schedule")
class ScheduleController extends AbstractController {

    ScheduleController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
