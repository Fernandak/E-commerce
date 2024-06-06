package com.ecommerce.pedidoapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.pedidoapi.circuitbreaker.CircuitBreakerProduto;
import com.ecommerce.pedidoapi.circuitbreaker.CircuitBreakerUser;
import com.ecommerce.pedidoapi.enums.StatusPedido;
import com.ecommerce.pedidoapi.feignclients.ProdutoFeignClient;
import com.ecommerce.pedidoapi.feignclients.UserFeignClient;
import com.ecommerce.pedidoapi.model.DetalhesPedidoModel;
import com.ecommerce.pedidoapi.model.PedidoModel;
import com.ecommerce.pedidoapi.model.ProdutoModel;
import com.ecommerce.pedidoapi.model.UserModel;
import com.ecommerce.pedidoapi.repository.DetalhesPedidoRepository;
import com.ecommerce.pedidoapi.repository.PedidoRepository;
import com.ecommerce.pedidoapi.service.PedidoService;

import jakarta.validation.Valid;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private ProdutoFeignClient produtoFeignClient;

	@Autowired
	private DetalhesPedidoRepository detalhesPedidoRepository;

	@Autowired
	private CircuitBreakerUser circuitBreakerUser;

	@Autowired
	private CircuitBreakerProduto circuitBreakerProduto;

	@Override
	public Optional<PedidoModel> registerPedido(@Valid PedidoModel pedidoModel) throws Exception {
		// verifica se o sistema esta dispovivel
		// chama metodo fallback caso api de usuario esteja indisponivel
		UserModel user = circuitBreakerUser.getUser(pedidoModel.getIdUsuario());
		PedidoModel pedido = new PedidoModel();
		double total_pedido = 0;
		if (user != null) {
			pedido = pedidoRepository.save(pedidoModel);
			// iteracao para pegar os detalhes do pedido
			List<DetalhesPedidoModel> detalhe = pedido.getDetalhesPedidoModel();
			for (DetalhesPedidoModel dpm : detalhe) {
				dpm.setIdPedido(pedidoModel.getIdPedido());
				Date date = new Date();
				pedidoModel.setDataCriacao(date);

				// validacao produto
				ProdutoModel buscaproduto = circuitBreakerProduto.getProduto(dpm.getIdProduto());

				// tratamento da baixa de estoque e validacao de quantidade
				if (buscaproduto.getEstoque() < dpm.getQuantidade()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade nao disponivel no estoque");
				} else {
					produtoFeignClient.setBaixaEstoque(dpm.getIdProduto(),
							buscaproduto.getEstoque() - dpm.getQuantidade().intValue());
				}
				// calcula o sub_total e total do pedido
				double sub_total = buscaproduto.getValorUnitario() * dpm.getQuantidade();
				dpm.setSubTotal(sub_total);
				total_pedido += sub_total;

				detalhesPedidoRepository.save(dpm);

			}
			pedidoModel.setTotalPedido(total_pedido);

			pedido = pedidoRepository.save(pedidoModel);

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse usuario nao existe");
		}

		return Optional.of(pedido);
	}

	@Override
	public List<PedidoModel> findAll() {

		return pedidoRepository.findAll();
	}

	@Override
	public Optional<PedidoModel> findById(Long id) {
		Optional<PedidoModel> pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" Desculpe, não foi possível encontrar um pedido com este id. Verifique e tente novamente.");
		} else {
			List<DetalhesPedidoModel> dpm = detalhesPedidoRepository
					.findDetalhesPedidoByPedido(pedido.get().getIdPedido());

			for (DetalhesPedidoModel d : dpm) {
				ProdutoModel produto = produtoFeignClient.getById(d.getIdProduto()).getBody();
				d.setProdutoModel(produto);

			}
			pedido.get().setDetalhesPedidoModel(dpm);

		}

		return pedido;
	}

	@Override
	public void deleteById(Long id) {

		Optional<PedidoModel> ped = pedidoRepository.findById(id);
		if (ped.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Não existe nenhum pedido com esse id. Verifique e tente novamente.");

		} else {
			if (ped.get().getStatusPedido() == StatusPedido.AGPAGAMENTO) {
				pedidoRepository.deletePedido(id);

			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao é legivel para exclusao");
			}
		}

	}

	@Override
	// busca por todos os status do pedidos de um usuario
	public List<PedidoModel> findAllByStatus(Long idUsuario, Integer statusPedido) {
		List<PedidoModel> buscaPorStatus = pedidoRepository.findAllByStatus(idUsuario, statusPedido);
		if (buscaPorStatus.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, não foi possível encontrar pedidos com esse status.");
		}
		for (PedidoModel pedido : buscaPorStatus) {

			List<DetalhesPedidoModel> detalhes = detalhesPedidoRepository
					.findDetalhesPedidoByPedido(pedido.getIdPedido());
			for (DetalhesPedidoModel idpm : detalhes) {
				idpm.setProdutoModel(produtoFeignClient.getById(idpm.getIdProduto()).getBody());
			}
			pedido.setDetalhesPedidoModel(detalhes);

		}

		return buscaPorStatus;
	}

	@Override
	// consultar todos os pedidos do usuario
	public List<PedidoModel> findAllByUsuario(Long idUsuario) {

		UserModel userModel = userFeignClient.getById(idUsuario).getBody();

		if (userModel == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, não foi possível retornar a lista de pedidos. O usuário informado não existe.");
		}

		if (!userModel.isAtivo()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Desculpe, não foi possível retornar a lista de pedidos. O usuário informado não existe.");
		}

		List<PedidoModel> pedidosUsuario = pedidoRepository.findAllByUsuario(idUsuario);
		if (pedidosUsuario.isEmpty()) {

			throw new ResponseStatusException(HttpStatus.OK, "[]");
		}

		for (PedidoModel buscaUsuario : pedidosUsuario) {
			List<DetalhesPedidoModel> detalhes = detalhesPedidoRepository
					.findDetalhesPedidoByPedido(buscaUsuario.getIdPedido());
			for (DetalhesPedidoModel bpu : detalhes) {
				bpu.setProdutoModel(produtoFeignClient.getById(bpu.getIdProduto()).getBody());

			}
			buscaUsuario.setDetalhesPedidoModel(detalhes);

		}

		return pedidosUsuario;
	}

}
