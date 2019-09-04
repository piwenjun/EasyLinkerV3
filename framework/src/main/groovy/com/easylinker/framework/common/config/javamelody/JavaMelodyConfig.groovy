package com.easylinker.framework.common.config.javamelody

import net.bull.javamelody.MonitoringFilter
import net.bull.javamelody.Parameter
import net.bull.javamelody.SessionListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

import javax.servlet.DispatcherType
import javax.servlet.ServletContext

@Configuration
class JavaMelodyConfig implements ServletContextInitializer{



    /**
     * 配置javamelody监控
     * spring boot 会按照order值的大小，从小到大的顺序来依次过滤
     */
    @Bean
    @Order(Integer.MAX_VALUE)
    FilterRegistrationBean<MonitoringFilter> monitoringFilter() {
        FilterRegistrationBean<MonitoringFilter> registration = new FilterRegistrationBean<MonitoringFilter>()
        registration.setFilter(new MonitoringFilter())
        registration.addUrlPatterns("/*")
        registration.setName("monitoring")
        return registration
    }

    /**
     *  配置javamelody监听器sessionListener
     */
    @Bean
    ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SessionListener> slrBean = new ServletListenerRegistrationBean<SessionListener>()
        slrBean.setListener(new SessionListener())
        return slrBean
    }

    @Override
     void onStartup(ServletContext servletContext) {
        servletContext.addListener(new SessionListener())
    }

    @Bean
     FilterRegistrationBean registrationBean() {
        FilterRegistrationBean javaMelody = new FilterRegistrationBean()
        javaMelody.setFilter(new MonitoringFilter())
        javaMelody.setAsyncSupported(true)
        javaMelody.setName("EasyLinkerV3性能监控")
        javaMelody.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC)
        javaMelody.addInitParameter(Parameter.LOG.getCode(), "true")
        return javaMelody
    }

}