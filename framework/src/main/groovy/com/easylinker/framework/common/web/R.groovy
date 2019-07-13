package com.easylinker.framework.common.web

import com.alibaba.fastjson.JSONObject

/**
 * 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
 *
 * 201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
 *
 * 202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
 *
 * 204 NO CONTENT - [DELETE]：用户删除数据成功。
 *
 * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
 *
 * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
 *
 * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
 *
 * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
 *
 * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
 *
 * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
 *
 * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
 *
 * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
 *
 * @author wwhai* @date 2019/6/4 22:07
 * @email:751957846 @qq.com瞅啥瞅？代码拿过来我看看有没有BUG。
 */
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
    static R error(String message) {
        R r = new R()
        r.put("code", ReturnEnum.FAIL.code)
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

    static R ok(String msg) {
        R r = new R()
        r.put("code", ReturnEnum.SUCCESS.code)
        r.put("msg", msg)
        r

    }

    static R noAuth() {
        R r = new R()
        r.put("code", 401)
        r.put("msg", "没有权限")
        r
    }

    static void main(String[] args) {
    }
}
