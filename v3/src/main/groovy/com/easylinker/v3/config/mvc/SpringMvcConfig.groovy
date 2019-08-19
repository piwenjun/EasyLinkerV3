package com.easylinker.v3.config.mvc

import com.easylinker.v3.config.security.GlobalSecurityFilter
import com.easylinker.v3.config.security.XssHttpServletRequestWrapper
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

import javax.servlet.*
import javax.servlet.http.HttpServletRequest

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

    @Bean
    FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>()
        filterRegistrationBean.setFilter(new XssFilter())
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST)
        filterRegistrationBean.setName("xssFilter")
        return filterRegistrationBean
    }


}

class XssFilter implements Filter {
    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request)
        chain.doFilter(xssRequest, response)
    }

    @Override
    void destroy() {}
}