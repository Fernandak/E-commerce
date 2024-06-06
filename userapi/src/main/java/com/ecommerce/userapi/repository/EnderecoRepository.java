package com.ecommerce.userapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.userapi.model.EnderecoModel;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {

	@Query(value = "select * from endereco where id_usuario = :id", nativeQuery = true)
	public List<EnderecoModel> findByIdUsuario(Long id);

	@Query(value = "select * from endereco where apelido = :apelido and id_usuario = :idUsuario LIMIT 1", nativeQuery = true)
	public EnderecoModel findByApelido(@Param(value = "idUsuario")Long idUsuario,@Param(value = "apelido") String apelido);

	@Query(value = "select * from endereco where id_endereco = :idEndereco and id_usuario= :idUsuario", nativeQuery = true)
	public Optional<EnderecoModel> findEnderecoByUsuario(Long idEndereco, Long idUsuario);
	
	//@Query(value = "select * from endereco where id_endereco = :id", nativeQuery = true)
	public EnderecoModel findByIdEndereco(Long id);

	public Optional<List<EnderecoModel>> findEnderecoByIdUsuario(Long idUsuario);	
	
}
