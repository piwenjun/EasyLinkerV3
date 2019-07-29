package com.easylinker.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@SpringBootApplication
class GatewayApplication {

    static void main(String[] args) {
        SpringApplication.run(GatewayApplication, args)
    }

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route({ r ->
                    r.path("/baidu").uri("http://www.baidu.com").filter(new GatewayFilter() {
                        @Override
                        Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                            println exchange.getRequest().getHeaders().get("token")
                            return null
                        }
                    })
                })
                .route({ r -> r.path("/taobao").uri("http://www.taobao.com") })
                .route({ r -> r.path("/hao123").uri("http://www.hao123.com") })
                .build()
    }
}
