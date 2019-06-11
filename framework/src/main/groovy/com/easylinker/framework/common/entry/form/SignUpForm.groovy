package com.easylinker.framework.common.entry.form

import lombok.Data

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * @author wwhai* @date 2019/6/5 20:20
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Data
class SignUpForm {
    @NotBlank(message = "登录口令不能为空")
    String principle
    @NotBlank(message = "登录密码不能为空")
    String password
    @NotBlank(message = "邮箱不能为空")
    @Email
    String email
    @NotBlank(message = "用户名不能为空")
    String name


}
