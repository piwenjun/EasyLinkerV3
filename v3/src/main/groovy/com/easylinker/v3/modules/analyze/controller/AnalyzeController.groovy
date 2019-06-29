package com.easylinker.v3.modules.analyze.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
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


    AnalyzeController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
