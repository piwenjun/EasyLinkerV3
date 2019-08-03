package com.easylinker.coapservice.config

import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.utils.CoapResource
import org.springframework.stereotype.Component

/**
 * [8-3日新增]Token认证通过Base64和私钥形式
 * B64字符串包含了设备的SID和TYPE
 * @author wwhai* @date 2019/8/3 18:04
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class B64TokenHandlerResource extends CoapResource {
    @Override
    void get(CoapExchange coapExchange) throws CoapCodeException {

    }

    @Override
    void post(CoapExchange exchange) throws CoapCodeException {
        super.post(exchange)
    }
}
