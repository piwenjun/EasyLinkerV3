package com.easylinker.servicegateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@EnableEurekaClient
@SpringBootApplication
class ServiceGatewayApplication {

    static void main(String[] args) {
        SpringApplication.run(ServiceGatewayApplication, args)
    }

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route({ r ->
                    r.path("/jianshu")  .uri("https://www.jianshu.com/p/1328898190e6").filter(new GatewayFilter() {
                        @Override
                        Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                            println "百度的请求:" + exchange.getRequest().remoteAddress
                            println(exchange.getRequest().getURI())
                            return chain.filter(exchange)
                        }
                    })
                }).build()
    }
}
