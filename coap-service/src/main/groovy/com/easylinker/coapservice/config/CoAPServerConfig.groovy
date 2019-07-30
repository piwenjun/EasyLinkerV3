package com.easylinker.coapservice.config

import com.easylinker.framework.common.web.R
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.network.CoapEndpoint
import org.eclipse.californium.core.network.config.NetworkConfig
import org.eclipse.californium.core.server.resources.CoapExchange
import org.eclipse.californium.elements.tcp.TcpServerConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

import java.util.concurrent.Executors

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

    @Bean
    CoapServer coapServer() {
        final CoapServer server = new CoapServer()
        server.addEndpoint(new CoapEndpoint(
                new TcpServerConnector(new InetSocketAddress(hostname, port), 10, 20000),
                NetworkConfig.getStandard()))

        /**
         * 添加CoAP协议处理器:考虑到COAP设备可能通过Get来传数据，所以两种形式都支持【用GET来提交数据在HTTP里面是不推荐的】
         * COAP设备上传的数据，必须有以下几个字段:
         * 1 token:String
         * 2 data:JSON 长度小于1024KB
         */
        server.add(new CoapResource("push") {
            @Override
            void handleGET(CoapExchange exchange) {
                if (exchange.getRequestText().length() > 1024) {
                    exchange.respond(R.error("Data too long").toJSONString())
                }
                String token = exchange.getQueryParameter("token")
                String data = exchange.getQueryParameter("data")
                if ((!token) || (!data)) {
                    exchange.respond(R.error("Missing argument").toJSONString())

                }


            };

            @Override
            void handlePOST(CoapExchange exchange) {
                System.out.println(exchange.getRequestPayload())

            }
        })
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            void run() {
                server.start()
            }
        })
        return server
    }
}
