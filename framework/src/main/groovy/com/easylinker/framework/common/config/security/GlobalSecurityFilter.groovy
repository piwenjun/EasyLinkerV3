package com.easylinker.framework.common.config.security

import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.framework.utils.JwtUtils
import org.springframework.lang.Nullable
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author wwhai* @date 2019/7/1 23:43
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class GlobalSecurityFilter implements HandlerInterceptor {
    private UserService userService

    GlobalSecurityFilter(UserService userService) {
        this.userService = userService
    }

    @Override
    Object invokeMethod(String s, Object o) {
        return super.invokeMethod(s, o)
    }

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!hasToken(request)) {
            return false
        }
        if (handler instanceof HandlerMethod) {
            RequireRole requireRole = handler.getMethodAnnotation(RequireRole.class)


            if (requireRole) {
                //开始查找Role
                AppUser appUser = userService.findByPrinciple(getCurrentUser(request).principle)
                String[] requireRoles = requireRole.roles()
                checkRole(requireRoles, appUser)

            }
        }
        println("进入拦截器")
        return true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    boolean hasToken(ServletRequest request) {
        println("isLoginAttempt拦截器开始作用")
        HttpServletRequest req = request as HttpServletRequest
        String authorization = req.getHeader("token")
        return authorization != null
    }

    boolean checkRole(String[] roles, AppUser appUser) {
        return true
    }

    boolean checkPermission(String[] permissions, Role role) {
        return true
    }

    /**
     * 获取当前User
     * @param request
     * @return
     */
    AppUser getCurrentUser(HttpServletRequest request) {

        String principle = JwtUtils.getMap(request.getHeader("token")).get("principle") as long

        return new AppUser(principle: principle)
    }


}
