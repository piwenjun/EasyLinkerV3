package com.easylinker.framework.common.config.security

import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.framework.utils.JwtUtils
import com.easylinker.framework.utils.RedisUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.Nullable
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Method

/**
 * @author wwhai* @date 2019/7/1 23:43
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
class GlobalSecurityFilter implements HandlerInterceptor {
    private UserService userService
    private List<String> allowList

    GlobalSecurityFilter(UserService userService) {
        this.userService = userService
        allowList = new ArrayList<>()
        allowList.add("/test")
        allowList.add("/entry/login")
        allowList.add("/entry/signUp")
        allowList.add("/entry/logOut")
        allowList.add("/captcha/jpg")


    }

    /**
     * 拦截器使用原理：
     * 1 先检查类上面的注解，然后获取roles，然后检查用户的roles，二者进行【AND】对比
     * 2 如果类注解没有，就会检查方法注解
     * 3 默认类注解覆盖方法注解
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isAllow(request)) {
            return true
        } else {
            if (!hasToken(request)) {
                throw new XException(401,"缺少token,或token已过期,请重新弄登录获取token")
            } else {
                if (handler instanceof HandlerMethod) {
                    //先检查类注解
                    String[] userRoles = JwtUtils.getMap(request).get("roles") as String[]
                    if (userRoles.length < 1) {
                        throw new XException(402,"用户权限不足")
                    }
                    Class controllerClazz = handler.beanType
                    RequireAuthRoles requireRole1 = controllerClazz.getAnnotation(RequireAuthRoles.class)

                    if (requireRole1) {
                        if (!checkRole(requireRole1.roles(), userRoles)) {
                            throw new XException(402,"请求权限不足")
                        } else {
                            return true

                        }
                    } else {
                        //检查方法注解
                        Method method = handler.method
                        RequireAuthRoles requireRole2 = method.getAnnotation(RequireAuthRoles.class)
                        if (requireRole2) {
                            if (!checkRole(requireRole2.roles(), userRoles)) {
                                throw new XException(402,"请求权限不足")
                            } else {
                                return true
                            }
                        } else {
                            //这里是没有检查出任何注解，就默认是公开的接口
                            //println("资源路径:" + request.getServletPath() + " 不需要权限匹配")
                            return true
                        }
                    }
                } else {
                    throw new XException("请求不支持")

                }
            }
        }
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

    /**
     * 判断用户是否想要登入。无论什么时候，合法的操作必须是Redis里面的token和用户的请求token相等
     * 检测header里面是否包含Authorization字段即可
     * 加入了 Token刷新机制，后来的会把之前的踢了
     */
    @Autowired
    RedisUtils redisUtils

    private boolean hasToken(ServletRequest request) {
        HttpServletRequest req = request as HttpServletRequest
        String token = req.getHeader("token")
        if (token == null || token.length() < 20) {
            return false
        }
        Map userData = JwtUtils.getMap(token)
        JSONObject catchUserData = JSONObject.parseObject(redisUtils.get("USER:" + userData.get("securityId")).toString())
        if (catchUserData) {
            String redisToken = catchUserData.getString("token")
            //println("RedisToken 和HttpToken是否相等:" + (redisToken == token))
            //throw new XException("Token 已过期,请重新登录获取!")
            return redisToken == token

        } else {
            //throw new XException("Token 已过期,请重新登录获取!")
            return false
        }
    }


    /**
     * 检查角色:
     * 这个原理讲一下：
     * 首先，获取认证的注解里面的role数组，然后对比用户史记拥有的数组
     * 两边数目一样才算是拥有权限
     * @param roles
     * @param appUser
     * @return
     */

    private static boolean checkRole(String[] requireRoles, String[] userRoles) {
        if (requireRoles.length == 0 || userRoles.length == 0) {
            return false
        }

        List<String> compareRoles = []
        for (String role : userRoles) {
            compareRoles.add(role)
        }
        int requiredRolesCount = requireRoles.length
        int realRolesCount = 0
        for (String role : requireRoles) {
            if (compareRoles.contains(role)) {
                realRolesCount += 1
            }
        }
        return requiredRolesCount == realRolesCount
    }

    /**
     * 检查放行的路径
     * @param request
     * @return
     */
    private boolean isAllow(HttpServletRequest request) {
        return allowList.contains(request.getServletPath())

    }

}
