package com.easylinker.v3.modules.analyze.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.config.security.RequireAuthRoles
import com.easylinker.v3.modules.analyze.service.AnalyzeService
import com.easylinker.v3.common.controller.AbstractController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 统计数据用的
 * @author wwhai* @date 2019/6/12 22:29
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/analyze")
class AnalyzeController extends AbstractController {

    @Autowired
    AnalyzeService analyzeService

    AnalyzeController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @GetMapping("/data")
    R analyzeDeviceData() {
        return R.okWithData(analyzeService.analyzeDeviceData(getCurrentUser()))
    }

    /**
     * 管理员的数据
     * @return
     */
    @RequireAuthRoles(roles = ['ADMIN'])
    @GetMapping("/adminData")
    R adminAnalyzeData() {
        return R.okWithData(analyzeService.analyzeDeviceData())
    }

    @RequireAuthRoles(roles = ['ADMIN'])
    @GetMapping("/systemInfo")
    R systemInfo() {
        return R.okWithData(analyzeService.systemInfo())

    }


}
