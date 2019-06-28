package com.easylinker.framework.common.web


import com.alibaba.fastjson.JSONObject
import lombok.Data

/**
 * @author wwhai* @date 2019/6/4 22:07
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@Data
class R extends JSONObject {
    private boolean status
    private Integer code
    private String msg
    private Object data


    static R data(Object data) {
        R r = new R()
        r.put("code", 1)
        if (data) {
            r.put("data", data)

        } else {
            r.put("data", [])

        }
        r.put("msg", ReturnEnum.SUCCESS)
        r
    }

    //--------------------------------------
    static R error(int code, String message) {
        R r = new R()
        r.put("code", code)
        r.put("data", [])
        r.put("msg", message)
        r

    }

    static R error(String message) {
        R r = new R()
        r.put("code", 0)
        r.put("data", [])
        r.put("msg", message)
        r
    }

    static R error() {
        R r = new R()
        r.put("code", 0)
        r.put("data", [])
        r.put("msg", ReturnEnum.FAIL)
        r
    }
    //----------------------------------------

    static R ok(int code, String message) {
        R r = new R()
        r.put("code", code)
        r.put("data", [])
        r.put("msg", message)
        r


    }

    static R ok(String message) {
        R r = new R()
        r.put("code", 1)
        r.put("data", [])
        r.put("msg", message)
        r
    }

    static R ok() {
        R r = new R()
        r.put("code", 1)
        r.put("data", [])
        r.put("msg", ReturnEnum.SUCCESS)
        r
    }

    static R ok(ReturnEnum returnEnum) {
        R r = new R()
        r.put("code", returnEnum.code)
        r.put("data", [])
        r.put("msg", returnEnum.message)
        r


    }

    static void main(String[] args) {
        println(ok("ok").toJSONString())
    }
}
