package com.easylinker.v3.modules.entry.form


import javax.validation.constraints.NotBlank

/**
 * @author wwhai* @date 2019/6/4 21:44
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */

class LoginForm {
    @NotBlank(message = "登录参数不能为空")
    private String principle
    @NotBlank(message = "登录密码不能为空")
    private String password
    @NotBlank(message = "验证码UUID不能为空")
    private String uuid
    @NotBlank(message = "验证码不能为空")
    private String captchaCode

    String getPrinciple() {
        return principle
    }

    void setPrinciple(String principle) {
        this.principle = principle
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getUuid() {
        return uuid
    }

    void setUuid(String uuid) {
        this.uuid = uuid
    }

    String getCaptchaCode() {
        return captchaCode
    }

    void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode
    }
}
