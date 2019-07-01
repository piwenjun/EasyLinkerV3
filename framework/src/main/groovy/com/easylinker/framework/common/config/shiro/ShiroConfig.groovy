package com.easylinker.framework.common.config.shiro

import com.easylinker.framework.common.config.jwt.JWTToken
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.service.RoleService
import com.easylinker.framework.modules.user.service.UserService
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.mgt.SessionStorageEvaluator
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.servlet.Filter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author wwhai* @date 2019/6/4 21:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

@Configuration
class ShiroConfig {
    @Bean
    AuthRealm authRealm(UserService userService, RoleService roleService) {
        return new AuthRealm(userService, roleService)
    }
    //权限管理，配置主要是Realm的管理认证
    @Bean("securityManager")
     SecurityManager securityManager(AuthRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        return securityManager;
    }

    @Bean
     ShiroFilterFactoryBean shiroFilter(AuthRealm authRealm) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager(authRealm));

        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new BasicHttpAuthenticationFilter() {

            @Override
            protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
                HttpServletRequest req = (HttpServletRequest) request;
                String authorization = req.getHeader("token");
                return authorization != null;
            }


            @Override
            protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

                if (isLoginAttempt(request, response)) {

                    try {
                        executeLogin(request, response);
                        return true;

                    } catch (Exception e) {
                        return false;
                    }


                } else {
                    return false;
                }
            }

            /**
             * 这里重写了父类的方法，使用我们自己定义的Token类，提交给shiro。这个方法返回null的话会直接抛出异常，进入isAccessAllowed（）的异常处理逻辑。
             */
            @Override
            protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
                return new JWTToken(((HttpServletRequest) servletRequest).getHeader("token"));

            }

            /**
             * 如果这个Filter在之前isAccessAllowed（）方法中返回false,则会进入这个方法。我们这里直接返回错误的response
             */
            @Override
            protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
                HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);
                httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
                servletResponse.getWriter().write(R.error(0, "Token异常，请检查是否存在或过期!").toString());
                servletResponse.getWriter().flush();
                return false;
            }

            /**
             * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
             */

            @Override
            protected boolean executeLogin(ServletRequest request, ServletResponse servletResponse) throws AuthenticationException {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                JWTToken token = new JWTToken(httpServletRequest.getHeader("token"));
                getSubject(request, servletResponse).login(token);
                return true;


            }

            /**
             * 提供跨域支持
             */
            @Override
            protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
                httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
                return true;
            }

        });
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "auth")
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }
}
