package com.easylinker.framework.common.config.mvc

import com.easylinker.framework.common.config.security.GlobalSecurityFilter
import com.easylinker.framework.modules.user.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class SpringMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    GlobalSecurityFilter globalSecurityFilter(UserService userService) {
        return new GlobalSecurityFilter(userService)
    }


    @Override
    void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(globalSecurityFilter()).addPathPatterns("/**");
        super.addInterceptors(registry)
    }

}
