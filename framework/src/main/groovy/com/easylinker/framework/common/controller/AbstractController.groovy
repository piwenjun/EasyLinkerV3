package com.easylinker.framework.common.controller

import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.utils.JwtUtils
import org.springframework.data.domain.PageRequest

import javax.servlet.http.HttpServletRequest

/**
 * 公共的基类
 * @param <T >
 */
abstract class AbstractController {
    private HttpServletRequest httpServletRequest

    abstract R getById(int id)

    abstract R deleteById(long id)

    abstract R list(int page ,int size)


    AbstractController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest
    }
    /**
     * 获取用户ID
     * @return
     */
    long getUserId() {

        JwtUtils.getPrinciple(httpServletRequest.getHeader("token")).get("userId") as long

    }

    AppUser getCurrentUser() {

        new AppUser(id: getUserId())
    }

    String getIp() {
        httpServletRequest.getRemoteHost()
    }

}
