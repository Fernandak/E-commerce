package com.ecommerce.pedidoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;

@RestController
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PedidoapiApplication {

	@RequestMapping("/")
    String home() {
        return "API de pedido";
    }
	public static void main(String[] args) {
		SpringApplication.run(PedidoapiApplication.class, args);
	}
	
    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("pedidoApi", builder -> builder.slidingWindowSize(100));
    }


}
