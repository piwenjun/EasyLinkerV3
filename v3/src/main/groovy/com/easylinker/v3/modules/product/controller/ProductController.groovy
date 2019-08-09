package com.easylinker.v3.modules.product.controller

import com.easylinker.framework.common.web.R
import com.easylinker.v3.common.controller.AbstractController
import com.easylinker.v3.modules.product.form.QueryForm
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 产品前端控制器
 */
@RestController
@RequestMapping("/product")
class ProductController extends AbstractController {
    ProductController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest)
    }

    @PostMapping
    R query(@RequestBody QueryForm queryForm) {

        R.ok()
    }
}
