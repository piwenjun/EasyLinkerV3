package com.easylinker.framework.common.web

/**
 * @author wwhai* @date 2019/6/4 22:16
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
enum ReturnEnum {
    SUCCESS(0, "操作成功"),
    FAIL(100, "系统异常,请联系管理员"),
    UN_LOGIN(200, "未登录"),
    INVALID_TOKEN(300, "Token异常"),
    UN_KNOW_ERROR(400, "未知异常"),


    private Integer code

    private String message


    ReturnEnum(Integer code, String message) {
        this.code = code
        this.message = message
    }

    public Integer getCode() {
        return code
    }

    public void setCode(Integer code) {
        this.code = code
    }

    public String getMessage() {
        return message
    }

    public void setMessage(String message) {
        this.message = message
    }

}
