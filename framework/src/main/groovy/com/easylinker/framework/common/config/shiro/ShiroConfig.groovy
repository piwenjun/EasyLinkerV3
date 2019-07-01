package com.easylinker.framework.common.config.shiro


import org.springframework.context.annotation.Configuration

/**
 * @author wwhai* @date 2019/6/4 21:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

@Configuration
class ShiroConfig {
//    @Bean
//    AuthRealm authRealm(UserService userService, RoleService roleService) {
//        return new AuthRealm(userService, roleService)
//    }
//
//    //权限管理，配置主要是Realm的管理认证
//    @Bean
//    SecurityManager securityManager(UserService userService, RoleService roleService) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager()
//        securityManager.setRealm(authRealm(userService, roleService))
//        return securityManager
//    }
//
////    //Filter工厂，设置对应的过滤条件和跳转条件
////    @Bean
////    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
////        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean()
////        shiroFilterFactoryBean.setSecurityManager(securityManager)
////        Map<String, String> map = new HashMap<String, String>()
////        //对所有用户认证
////        map.put("/**", "authc")
////        shiroFilterFactoryBean.setFilterChainDefinitionMap(map)
////        return shiroFilterFactoryBean
////    }
//
//    /**
//     * 设置过滤器，将自定义的Filter加入
//     */
//    @Bean("shiroFilter")
//    ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean()
//        factoryBean.setSecurityManager(securityManager)
//        Map<String, Filter> filterMap = factoryBean.getFilters()
//        filterMap.put("authFilter", new ShiroAuthFilter())
//
//        factoryBean.setFilters(filterMap)
//        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap())
//        return factoryBean
//    }
//
//    @Bean
//    ShiroFilterChainDefinition shiroFilterChainDefinition() {
//        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition()
//        //只允许admin或manager角色的用户访问
//        chainDefinition.addPathDefinition("/**", "authFilter") // 默认进行用户鉴权
//        return chainDefinition
//    }
//    //加入注解的使用，不加入这个注解不生效
//    @Bean
//    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor()
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager)
//        return authorizationAttributeSourceAdvisor
//    }
//
//    @Bean
//    SessionStorageEvaluator sessionStorageEvaluator() {
//        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator()
//        sessionStorageEvaluator.setSessionStorageEnabled(false)
//        return sessionStorageEvaluator
//    }
}
