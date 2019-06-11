package com.easylinker.framework.common.entry.controller


import com.easylinker.framework.common.entry.form.LoginForm
import com.easylinker.framework.common.entry.form.SignUpForm
import com.easylinker.framework.common.exception.XException
import com.easylinker.framework.common.web.R
import com.easylinker.framework.modules.user.model.AppUser
import com.easylinker.framework.modules.user.model.Role
import com.easylinker.framework.modules.user.service.RoleService
import com.easylinker.framework.modules.user.service.UserService
import com.easylinker.framework.utils.CaptchaUtils
import com.easylinker.framework.utils.JwtUtils
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

/**
 * @author wwhai* @date 2019/6/4 21:43
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@RestController
@RequestMapping("/entry")
class EntryController {
    @Autowired
    CaptchaUtils captchaUtils
    @Autowired
    UserService userService

    @Autowired
    RoleService roleService

    @PostMapping("/login")
    R login(@Valid @RequestBody LoginForm loginForm) {
        if (!captchaUtils.validate(loginForm.uuid, loginForm.captchaCode)) {

            throw new XException(0, "验证码识别失败!")
        }

        AppUser appUser = userService.findByPrinciple(loginForm.getPrinciple())
        if (appUser && appUser.getPassword().equals(DigestUtils.sha256Hex(loginForm.getPassword()))) {
            Map<String, Object> map = new HashMap<>()
            map.put("principle", appUser.principle)
            map.put("userId", appUser.id)

            R.data(JwtUtils.token(map))
        } else {
            throw new XException(0, "登陆失败!")
        }
    }

    /**
     * 注册
     * @param baseForm
     * @return
     */

    @PostMapping("/signUp")
    R signUp(@Valid @RequestBody SignUpForm signUpForm) {
        AppUser appUser = new AppUser(principle: signUpForm.principle,
                password: DigestUtils.sha256Hex(signUpForm.password),
                email: signUpForm.email,
                name: signUpForm.name
        )
        userService.save(appUser)
        roleService.save(new Role(name: "BASE_ROLE", info: "基本权限", appUser: appUser))

        R.ok("注册成功!")

    }
}
