package com.easylinker.framework.common.web

/**
 * @author wwhai* @date 2019/6/4 22:16
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
enum ReturnEnum {
    SUCCESS(0, "操作成功"),
    FAIL(100, "操作失败"),
    INVALID_TOKEN(401, "Token异常"),
    NO_AUTH(402, "Token异常"),
    UN_LOGIN(403, "未登录"),
    NOT_FOUND(404, "资源不存在"),
    UN_KNOW_ERROR(500, "未知异常"),


    private Integer code

    private String message

    @Override
    String toString() {
        return name()
    }

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
