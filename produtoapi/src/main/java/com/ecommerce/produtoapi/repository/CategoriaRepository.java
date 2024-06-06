package com.ecommerce.produtoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.produtoapi.model.CategoriaModel;

import jakarta.transaction.Transactional;
@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

	Optional<CategoriaModel> findByIdCategoria(Object findByIdCategoria);

	Optional<CategoriaModel> findAllByNomeCategoriaContainingIgnoreCase(String nomeCategoria);

	@Query(value = "UPDATE categoria SET nome_categoria = :nomeCategoria WHERE id_categoria = :id", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer updateCategoria(@Param("id") Long id, String nomeCategoria);

}
