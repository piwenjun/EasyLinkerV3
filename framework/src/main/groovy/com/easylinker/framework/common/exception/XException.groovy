package com.easylinker.framework.common.exception
/**
 * @author wwhai* @date 2019/6/4 22:09
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */

class XException extends Exception {
    private int code = 501
    private String message = "ERROR"

    XException(int code, String message) {
        super(message)
        this.code = code
        this.message = message
    }

    XException(String message) {
        super(message)
        this.message = message
    }


    int getCode() {
        return code
    }

    void setCode(int code) {
        this.code = code
    }

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }
}
