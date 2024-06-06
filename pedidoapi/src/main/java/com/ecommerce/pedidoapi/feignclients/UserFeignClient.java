package com.ecommerce.pedidoapi.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.pedidoapi.model.UserModel;

@Component
@FeignClient(name = "userapi", path = "/user")
public interface UserFeignClient {
	
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getById(@PathVariable Long id);

}
