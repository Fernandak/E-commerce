package com.ecommerce.userapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.userapi.model.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	@Query(value = "select * from usuario where cpf = :cpf", nativeQuery = true)
	public Optional<UserModel> buscaPorCpf(@Param(value = "cpf") String cpf);

	public List<UserModel> findAllByNomeContainingIgnoreCase(String nome);

	@Query(value = "UPDATE Usuario SET ativo = FALSE WHERE id_usuario = :id", nativeQuery = true)
	@Modifying
	@Transactional  //Descreve um atributo de transação em um método individual ou em uma classe
	public void inativarUsuario(@Param("id") Long id);

	@Query(value = "UPDATE usuario SET ativo = true WHERE id_usuario = :id", nativeQuery = true)
	@Modifying
	@Transactional
	public void ativarUsuario(@Param("id") Long id);
	
	Optional<UserModel> findByEmail(String email);

}
