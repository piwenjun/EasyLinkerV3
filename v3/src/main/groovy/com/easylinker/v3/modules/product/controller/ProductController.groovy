package com.easylinker.v3.modules.product.controller

import com.easylinker.v3.common.controller.AbstractController
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
}
