package com.easylinker.framework.modules.plugin.controller

import com.easylinker.framework.common.controller.AbstractController
import com.easylinker.framework.common.web.R
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * Created by admin on 2019/6/5 16:47
 *
 */

@RestController
@RequestMapping("/plugins")
class PluginController extends AbstractController {


    PluginController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @Override
    R getById(int id) {
        return null
    }

    @Override
    R deleteById(long id) {
        R.ok()

    }

    @Override
    R list(int page, int size) {
        return null
    }


}
