package com.easylinker.v3.common.controller

import com.easylinker.framework.common.exception.XException
import com.easylinker.v3.modules.user.model.AppUser
import com.easylinker.v3.utils.JwtUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest

/**
 * 公共的基类
 * @param <T >
 */
abstract class AbstractController {
    protected final static Logger logger = LoggerFactory.getLogger(getClass())
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

        new AppUser(id: getUserId(), securityId: getSecurityId(), createTime: null, isDelete: false, updateTime: null)
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
            logger.error("缺少Token")
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
            logger.error("Token不合法")

            throw new XException("Token不合法")

        }
    }
}
