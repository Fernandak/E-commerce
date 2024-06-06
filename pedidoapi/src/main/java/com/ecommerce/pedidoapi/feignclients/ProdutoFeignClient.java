package com.ecommerce.pedidoapi.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerce.pedidoapi.model.ProdutoModel;

@Component
@FeignClient(name = "produtoapi", path = "/produto")
public interface ProdutoFeignClient {

	
	@GetMapping("id/{id}")
	public ResponseEntity<ProdutoModel> getById(@PathVariable Long id);
	
	@PostMapping("baixaEstoque/{id}/{quantidade}")
	public ResponseEntity<ProdutoModel> setBaixaEstoque(@PathVariable Long id, @PathVariable int quantidade);
	
}
