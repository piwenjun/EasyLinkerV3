package com.easylinker.coapservice.config

import com.mbed.coap.server.CoapServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

/**
 * @author wwhai* @date 2019/7/30 21:26
 * @email:751957846 @qq.com
 * 瞅啥瞅？代码拿过来我看看有没有BUG。
 *
 */
@Component
class CoAPServerConfig {
    private Logger logger = LoggerFactory.getLogger(getClass())
    @Value(value = '${coap.server.hostname}')
    String hostname
    @Value(value = '${coap.server.port}')
    int port
    @Autowired
    CacheTokenResource cacheTokenResource
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

         * --------------------------------------------------------------------------------------------
         * 1 消息到来的时候 先检查【token】的合法性【这里需要调用V3的一个接口：/coap/isValid?token=[token]】
         *   [问题：一个请求会经过两次微服务，势必会影响性能，考虑如何优化这部分]
         * 2 如果【token】不合法，就不予处理
         * 3 如果【token】合法，保存数据到数据库
         * --------------------------------------------------------------------------------------------
         * 备注：关于这个Token的认证形式，初步先用最简单的数据库查找认证【但是真的很消耗性能】
         * 后面准备用非对称加密算法来认证
         * --------------------------------------------------------------------------------------------
         */

        server.addRequestHandler("/post", cacheTokenResource)
        server.start()
        if (server.isRunning()) {
            logger.info("-----------------------------------------------------")
            logger.info("|| CoAP Server Running Success At Port :" + port)
            logger.info("-----------------------------------------------------")

        }
        return server
    }


}


