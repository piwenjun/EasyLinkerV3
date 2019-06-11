package com.easylinker.framework.common.config.shiro

import com.easylinker.framework.common.config.jwt.JWTToken
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 认证的过滤器
 */
@Component
class AuthFilter extends BasicHttpAuthenticationFilter {
    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request
        String authorization = req.getHeader("token")
        authorization != null
    }

    /**
     * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。
     * 这里在不通过时，还调用了isPermissive()方法，我们后面解释。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response)
                true
            } catch (e) {
                throw e
                //false
            }

        } else {
            false
        }
    }

/**
 * 这里重写了父类的方法，使用我们自己定义的Token类，提交给shiro。这个方法返回null的话会直接抛出异常，进入isAccessAllowed（）的异常处理逻辑。
 */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        new JWTToken((servletRequest as HttpServletRequest).getHeader("token"))
    }
    /**
     * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse)
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8")
        servletResponse.getWriter().write(R.error("请求失败，缺少Token!").toJSONString())
        servletResponse.getWriter().flush()

        false
    }
    /**
     *  如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse servletResponse) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request
        JWTToken token = new JWTToken(httpServletRequest.getHeader("token"))
        getSubject(request, servletResponse).login(token)
        true

    }

/**
 * 提供跨域支持
 */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request
        HttpServletResponse httpServletResponse = (HttpServletResponse) response
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"))
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE")
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"))
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod() == RequestMethod.OPTIONS.name()) {
            httpServletResponse.setStatus(HttpStatus.OK.value())
            false
        } else {
            true
        }
    }
}

