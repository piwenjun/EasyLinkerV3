package com.easylinker.coapservice.config

import cn.hutool.http.HttpUtil
import com.alibaba.fastjson.JSONObject
import com.easylinker.framework.common.web.R
import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.packet.Code
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.server.CoapServer
import com.mbed.coap.utils.CoapResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

/**
 * @author wwhai* @date 2019/7/30 21:26
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class CoAPServerConfig {
    @Autowired
    MongoTemplate mongoTemplate
    @Value(value = '${coap.server.hostname}')
    String hostname
    @Value(value = '${coap.server.port}')
    int port
    /**
     * 添加CoAP协议处理器:考虑到COAP设备可能通过Get来传数据，所以两种形式都支持【用GET来提交数据在HTTP里面是不推荐的】
     * COAP设备上传的数据，必须有以下几个字段:
     * 1 token:String
     * 2 data:JSON 长度小于1024KB
     */

    @Bean
    CoapServer coapServer() {
        final CoapServer server = CoapServer.builder() .transport(port).build()
        /**
         * 【重要】此接口需要限制频率，目前先限制为每个客户端的上传时间间隔为2秒;具体
         * 实现方法：push的时候，先redis.get(deviceSecurityId);如果没有拿到说明没有限制，数据放行;
         * 否则拦截并且:redis.set(deviceSecurityId,2s)
         * --------------------------------------------------------------------------------------------
         * 1 消息到来的时候 先检查【token】的合法性【这里需要调用V3的一个接口：/coap/isValid?token=[token]】
         *  [问题：一个请求会经过两次微服务，势必会影响性能，考虑如何优化这部分]
         * 2 如果【token】不合法，就不予处理
         * 3 如果【token】合法，保存数据到数据库
         * --------------------------------------------------------------------------------------------
         * 备注：关于这个Token的认证形式，初步先用最简单的数据库查找认证【但是真的很消耗性能】
         * 后面准备用非对称加密算法来认证
         * --------------------------------------------------------------------------------------------
         */

        server.addRequestHandler("/push", new CoapResource() {
            @Override
            void get(CoapExchange coapExchange) throws CoapCodeException {
            }

            @Override
            void post(CoapExchange coapExchange) throws CoapCodeException {
                if (!coapExchange.getRequestBodyString()) {
                    return
                }
                if (coapExchange.getRequestBodyString().length() > 1024) {
                    coapExchange.setResponseBody(R.error("Data too long!Most 1024KB!").toJSONString())
                    coapExchange.setResponseCode(Code.C205_CONTENT)
                    coapExchange.sendResponse()
                } else {
                    try {
                        /**
                         * String token【必填】
                         * String data【必填】
                         * String unit【选填】
                         * String deviceSecurityId【必填】
                         * String info【选填】
                         */

                        //JSON 可能解析异常
                        JSONObject dataJson = JSONObject.parseObject(coapExchange.request.payloadString)
                        //数据入库请求

                        String token = dataJson.getString("token")
//                        String deviceSecurityId = dataJson.getString("deviceSecurityId")
//                        String data = dataJson.getString("token")
//                        String unit = dataJson.getString("unit")
//                        String info = dataJson.getString("info")
                        //请求【V3】保存数据
                        if (!token) {
                            coapExchange.setResponseBody(R.ok("Invalid token!").toJSONString())
                            coapExchange.setResponseCode(Code.C205_CONTENT)
                            coapExchange.sendResponse()
                        }
                        //推送
                        String result = HttpUtil.post("V3的地址", dataJson)
                        coapExchange.setResponseBody(R.ok(result).toJSONString())
                        coapExchange.setResponseCode(Code.C205_CONTENT)
                        coapExchange.sendResponse()
                    } catch (Exception e) {
                        coapExchange.setResponseBody(R.ok("Data format mast be define json!").toJSONString())
                        coapExchange.setResponseCode(Code.C205_CONTENT)
                        coapExchange.sendResponse()
                    }

                }
            }
        })
        server.start()
        return server
    }
}