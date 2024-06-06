package com.ecommerce.userapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.userapi.model.PaisModel;

@Repository
public interface PaisRepository extends JpaRepository<PaisModel, Long>{
	
	@Query(value = "select * from country where nome = :nome", nativeQuery = true)
	public Optional<PaisModel> buscaPorNome(@Param(value = "nome") String nome);

}
