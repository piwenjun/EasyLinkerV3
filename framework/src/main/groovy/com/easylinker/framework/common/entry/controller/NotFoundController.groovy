package com.easylinker.framework.common.entry.controller

import com.easylinker.framework.common.web.R
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * @author wwhai* @date 2019/6/6 22:19
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

@RestController
class NotFoundController implements ErrorController {


    private static final int NOT_FOUND = 404
    private static final String ERROR_PATH = "/error"

    @RequestMapping(value = "/error")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    R handleError() {
        R.error(404, "经典404")
    }

    @Override
    String getErrorPath() {
        return "/error"
    }
}
