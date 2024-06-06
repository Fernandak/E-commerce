package com.ecommerce.produtoapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.produtoapi.model.SubCategoriaModel;

import jakarta.transaction.Transactional;
@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoriaModel, Long> {

	Optional<SubCategoriaModel> findAllByNomeSubCategoriaContainingIgnoreCase(String nomeSubCategoria);

	@Query(value = "UPDATE Sub_categoria SET nome_sub_categoria = :#{#subCategoriaModel.nomeSubCategoria}, descricao ="
			+ " :#{#subCategoriaModel.descricao},id_categoria= :#{#subCategoriaModel.idCategoria} WHERE id_sub_categoria = :id", nativeQuery = true)
	@Modifying
	@Transactional
	public Integer updateSubCategoria(@Param("id") Long id, SubCategoriaModel subCategoriaModel);

	@Query(value = "select * from sub_categoria where id_categoria = :idCategoria", nativeQuery = true)
	List<SubCategoriaModel> findByIdCategoria(Long idCategoria);
}
