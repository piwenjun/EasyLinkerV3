package com.easylinker.v3.modules.message.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * Created by admin on 2019/6/11 10:19
 *
 */

@RestController
@RequestMapping("/msg")
class MessageController extends AbstractController{

    MessageController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }
}
