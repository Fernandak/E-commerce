package com.ecommerce.produtoapi.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.produtoapi.model.ProdutoModel;

import jakarta.transaction.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
	
	List<ProdutoModel> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);

	@Query(value = "select * from produto where sku = :sku", nativeQuery = true)
	List<ProdutoModel> findBySKU(int sku);

	@Query(value = "select estoque from produto where idProduto = :idProduto", nativeQuery = true)
	int getQuantidadeEstoque(@Param(value = "idProduto") Long idProduto);

	@Query(value = "UPDATE produto SET estoque = :quantidade WHERE id_produto = :idProduto", nativeQuery = true)
	@Modifying
	@Transactional
	int setBaixaEstoque(Long idProduto, int quantidade);

	@Query(value = "select * from produto where id_categoria = :idCategoria", nativeQuery = true)
	List<ProdutoModel> findProdutoByCategoria(Long idCategoria);
	
	
	
	
	

	
}
