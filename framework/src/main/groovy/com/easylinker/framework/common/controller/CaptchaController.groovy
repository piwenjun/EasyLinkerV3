package com.easylinker.framework.common.controller

import com.easylinker.framework.utils.CaptchaUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.imageio.ImageIO
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
import java.awt.image.BufferedImage

/**
 * @author wwhai* @date 2019/6/4 23:02
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/captcha")
class CaptchaController {
    @Autowired
    CaptchaUtils captchaUtils
    /**
     * 验证码
     */
    @GetMapping("/jpg")
    void captcha(HttpServletResponse response, @RequestParam String uuid) {
        response.setHeader("Cache-Control", "no-store, no-cache")
        response.setContentType("image/jpeg")
        //获取图片验证码
        BufferedImage image = captchaUtils.getCaptcha(uuid)
        ServletOutputStream out = response.getOutputStream()
        ImageIO.write(image, "jpg", out)
        out.flush()
        out.close()
    }

}
