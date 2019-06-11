package com.easylinker.framework.common.config.shiro

import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.mgt.SessionStorageEvaluator
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.servlet.Filter

/**
 * @author wwhai* @date 2019/6/4 21:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Configuration
class ShiroConfig {

    @Autowired
    AuthRealm authRealm

    //权限管理，配置主要是Realm的管理认证
    @Bean("securityManager")
    SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager()
        securityManager.setRealm(authRealm)
        securityManager.setRememberMeManager(null)
        return securityManager
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean()
        shiroFilter.setSecurityManager(securityManager)
        Map<String, Filter> filters = new HashMap<>()
        filters.put("auth", new AuthFilter())
        shiroFilter.setFilters(filters)
        Map<String, String> filterMap = new LinkedHashMap<>()
        filterMap.put("/entry/login", "anon")
        filterMap.put("/entry/signUp", "anon")
        filterMap.put("/captcha/jpg", "anon")
        filterMap.put("/test", "anon")
        filterMap.put("/error", "anon")
        filterMap.put("/**", "auth")
        shiroFilter.setFilterChainDefinitionMap(filterMap)

        return shiroFilter
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor()
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager)
        return authorizationAttributeSourceAdvisor
    }

    @Bean
    SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator()
        sessionStorageEvaluator.setSessionStorageEnabled(false)
        return sessionStorageEvaluator
    }
}
