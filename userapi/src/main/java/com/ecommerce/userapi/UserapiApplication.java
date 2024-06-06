package com.ecommerce.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class UserapiApplication {

	
	@RequestMapping("/")
    String home() {
        return "API de Usuário";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(UserapiApplication.class, args);
	}

}
