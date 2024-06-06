package com.ecommerce.produtoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ProdutoapiApplication {
	@RequestMapping("/")
    String home() {
        return "API de Produto!";
    }

	public static void main(String[] args) {
		SpringApplication.run(ProdutoapiApplication.class, args);
		
	    }
}
