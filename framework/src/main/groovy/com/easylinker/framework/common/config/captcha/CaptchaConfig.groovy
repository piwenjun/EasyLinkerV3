package com.easylinker.framework.common.config.captcha

/**
 * @author wwhai* @date 2019/6/4 22:59
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 生成验证码配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties()
        // 图片边框
        properties.setProperty("kaptcha.border", "yes")
        // 边框颜色
        //properties.setProperty("kaptcha.border.color", "105,179,90")
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue")
        // 图片宽
        properties.setProperty("kaptcha.image.width", "140")
        // 图片高
        properties.setProperty("kaptcha.image.height", "40")
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30")
        // session key
        properties.setProperty("kaptcha.session.key", "code")
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "6")
        // 字体
        //properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑")

        Config config = new Config(properties)
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha()
        defaultKaptcha.setConfig(config)
        return defaultKaptcha
    }
}