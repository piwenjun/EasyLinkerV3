package com.easylinker.framework.common.controller

import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.utils.JwtUtils

import javax.servlet.http.HttpServletRequest

/**
 * 公共的基类
 * @param < T >
 */
abstract class AbstractController {
    private HttpServletRequest httpServletRequest

    AbstractController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest
    }

    HttpServletRequest getHttpServletRequest() {
        return httpServletRequest
    }

    void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest
    }
/**
 * 获取用户ID
 * @return
 */
    long getUserId() {

        JwtUtils.getMap(getToken()).get("id") as long

    }
    //securityId
    String getSecurityId() {
        JwtUtils.getMap(getToken()).get("securityId") as String
    }

    AppUser getCurrentUser() {

        new AppUser(id: getUserId(), securityId: getSecurityId())
    }

    String getIp() {
        httpServletRequest.getRemoteHost()
    }
    /**
     * 获取Token
     * @param httpServletRequest
     * @return
     */

    String getToken() {
        if (httpServletRequest.getHeader("token")) {
            return httpServletRequest.getHeader("token")

        } else {
            throw new XException("请求缺少Token")
        }
    }
    /**
     * 获取当前的jwt
     * @return
     */
    Map<String, Object> getJwtMap() {
        if (JwtUtils.verify(getToken())) {
            return JwtUtils.getMap(getToken())

        } else {
            throw new XException("Token不合法")

        }
    }
}
