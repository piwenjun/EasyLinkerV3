package com.easylinker.v3.config.mvc

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.easylinker.v3.config.security.GlobalSecurityFilter
import com.easylinker.v3.config.security.XssHttpServletRequestWrapper
import com.easylinker.v3.modules.user.service.UserService
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Configuration
class SpringMvcConfig extends WebMvcConfigurationSupport implements WebMvcConfigurer {

    @Bean
    GlobalSecurityFilter globalSecurityFilter(UserService userService) {
        return new GlobalSecurityFilter(userService)
    }


    @Override
    void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(globalSecurityFilter()).addPathPatterns("/**")
        super.addInterceptors(registry)
    }

    @Override
    void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true).maxAge(3600)
    }

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Override
    void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建fastJson消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter()

        //升级最新版本需加=============================================================
        List<MediaType> supportedMediaTypes = new ArrayList<>()
        supportedMediaTypes.add(MediaType.APPLICATION_JSON)
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8)
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML)
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED)
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM)
        supportedMediaTypes.add(MediaType.APPLICATION_PDF)
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML)
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML)
        supportedMediaTypes.add(MediaType.APPLICATION_XML)
        supportedMediaTypes.add(MediaType.IMAGE_GIF)
        supportedMediaTypes.add(MediaType.IMAGE_JPEG)
        supportedMediaTypes.add(MediaType.IMAGE_PNG)
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM)
        supportedMediaTypes.add(MediaType.TEXT_HTML)
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN)
        supportedMediaTypes.add(MediaType.TEXT_PLAIN)
        supportedMediaTypes.add(MediaType.TEXT_XML)
        fastConverter.setSupportedMediaTypes(supportedMediaTypes)

        //创建配置类
        FastJsonConfig fastJsonConfig = new FastJsonConfig()
        //修改配置返回内容的过滤
        //WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
        //WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
        //DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
        //WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
        //WriteMapNullValue：是否输出值为null的字段,默认为false
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue
        )
        fastConverter.setFastJsonConfig(fastJsonConfig)
        //将fastjson添加到视图消息转换器列表内
        converters.add(fastConverter)
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