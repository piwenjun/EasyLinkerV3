package com.easylinker.framework.common.web


import com.alibaba.fastjson.JSONObject
import lombok.Data

/**
 * @author wwhai* @date 2019/6/4 22:07
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
@Data
class R extends JSONObject {

    /**
     * 成功返回数据
     * @param data
     * @return
     */
    static R okWithData(Object data) {
        R r = new R()
        r.put("code", ReturnEnum.SUCCESS.code)
        if (data) {
            r.put("data", data)

        } else {
            r.put("data", [])

        }
        r.put("msg", ReturnEnum.SUCCESS.message)
        r
    }
    /**
     * 错误返回数据
     * @param data
     * @return
     */


    static R errorWithData(Object data) {
        R r = new R()
        r.put("code", ReturnEnum.SUCCESS.code)
        if (data) {
            r.put("data", data)

        } else {
            r.put("data", [])

        }
        r.put("msg", ReturnEnum.SUCCESS.message)
        r
    }
    //--------------------------------------
    /**
     * 自定义错误
     * @param code
     * @param message
     * @return
     */
    static R error(int code, String message) {
        R r = new R()
        r.put("code", code)
        r.put("msg", message)
        r

    }
    /**
     * 内置错误
     * @return
     */

    static R error() {
        R r = new R()
        r.put("code", ReturnEnum.FAIL.code)
        r.put("msg", ReturnEnum.FAIL.message)
        r
    }
    /**
     * 内置错误
     * @param returnEnum
     * @return
     */

    static R error(ReturnEnum returnEnum) {
        R r = new R()
        r.put("code", returnEnum.code)
        r.put("msg", returnEnum.message)
        r
    }
    //----------------------------------------
    /**
     * 自定义成功
     * @param code
     * @param message
     * @return
     */
    static R ok(int code, String message) {
        R r = new R()
        r.put("code", code)
        r.put("msg", message)
        r

    }
    /**
     * 内置成功
     * @return
     */
    static R ok() {
        R r = new R()
        r.put("code", ReturnEnum.SUCCESS.code)
        r.put("msg", ReturnEnum.SUCCESS.message)
        r
    }
    /**
     * 内置成功
     * @param returnEnum
     * @return
     */
    static R ok(ReturnEnum returnEnum) {
        R r = new R()
        r.put("code", returnEnum.code)
        r.put("msg", returnEnum.message)
        r

    }

    static void main(String[] args) {
    }
}
