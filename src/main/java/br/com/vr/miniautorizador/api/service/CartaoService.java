package br.com.vr.miniautorizador.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.domain.repository.CartaoRepository;
import br.com.vr.miniautorizador.api.model.CartaoModel;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ModelMapper modelMapper;	

	public ResponseEntity<CartaoModel> novoCartao(Cartao cartao) {

		Optional<Cartao> optCartao = getCartao(cartao.getNumeroCartao());

		if (!optCartao.isPresent()) {

			Cartao novoCartao = new Cartao(cartao.getNumeroCartao(), cartao.getSenha(),new BigDecimal("500.00") );
			novoCartao.setDtUpdate(new Date());
			cartaoRepository.save(novoCartao);

			return ResponseEntity.status(201).body(toCartaoModel(novoCartao));
		} else {

			return ResponseEntity.status(422).body(toCartaoModel(optCartao.get()));
		}

	}

	public ResponseEntity<CartaoModel> getSaldo(String cartao) {

		Optional<Cartao> optCartao = getCartao(cartao);

		return optCartao.isPresent() ? ResponseEntity.status(200).body(toCartaoModelSaldo(optCartao.get()))
				: ResponseEntity.status(404).build();
	}

	public Optional<Cartao> getCartao(String cartao) {

		return cartaoRepository.findById(cartao);

	}
	
	public CartaoModel toCartaoModel(Cartao cartao) {
		CartaoModel cartaoModel = modelMapper.map(cartao, CartaoModel.class);
		cartaoModel.setSaldo(null);
		return cartaoModel;
	}

	public CartaoModel toCartaoModelSaldo(Cartao cartao) {
		CartaoModel cartaoSaldo = modelMapper.map(cartao, CartaoModel.class);
		cartaoSaldo.setNumeroCartao(null);
		cartaoSaldo.setSenha(null);
		return cartaoSaldo;
	}
	
	
}
