package com.easylinker.framework.utils

import com.easylinker.framework.common.exception.XException
import com.google.code.kaptcha.Producer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.awt.image.BufferedImage

/**
 * @author wwhai* @date 2019/6/4 23:03
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

@Component
class CaptchaUtils {
    @Autowired
    Producer producer

    BufferedImage getCaptcha(String uuid) {
        if (!uuid) {
            throw new XException(500, "uuid不能为空")
        }
        String code = producer.createText()
        println("生成的验证码:${code},对应的UUID:${uuid}")
        producer.createImage(code)
    }
    /**
     * 验证码验证
     * @param uuid
     * @param captchaCode
     * @return
     */
    boolean validate(String uuid, String captchaCode) {
        //1 根据uuid在数据库查找code
        //2 判断是否和穿进来的凑得匹配
        //3 返回结果 同时删除记录
        true

    }

}
