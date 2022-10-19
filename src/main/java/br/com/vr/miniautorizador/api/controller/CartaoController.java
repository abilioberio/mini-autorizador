package br.com.vr.miniautorizador.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.model.CartaoModel;
import br.com.vr.miniautorizador.api.service.CartaoService;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<CartaoModel> novoCartao(@RequestBody Cartao cartao) {

		return cartaoService.novoCartao(cartao) != null ? ResponseEntity.status(201).body(toCartaoModel(cartao))
				: ResponseEntity.status(422).body(toCartaoModel(cartao));

	}

	@GetMapping("/{numeroCartao}")
	public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {

		BigDecimal saldo = cartaoService.getSaldo(numeroCartao);
		return saldo == null ? ResponseEntity.status(404).build() : ResponseEntity.status(200).body(saldo);
	}
	
	@GetMapping
	public ResponseEntity<List<Cartao>> getCartoes() {
		List<Cartao> lista = cartaoService.getCartoes();
		return ResponseEntity.status(200).body(lista);
	}
	

	public CartaoModel toCartaoModel(Cartao cartao) {
		CartaoModel cartaoModel = modelMapper.map(cartao, CartaoModel.class);
		return cartaoModel;
	}

	public Cartao toModelCartao(CartaoModel cartaoModel) {
		return modelMapper.map(cartaoModel, Cartao.class);

	}
}
