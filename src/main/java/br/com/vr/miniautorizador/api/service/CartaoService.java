package br.com.vr.miniautorizador.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.domain.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;

	public ResponseEntity<Cartao> novoCartao(Cartao cartao) {

		Optional<Cartao> optCartao = getCartao(cartao.getNumeroCartao());

		if (!optCartao.isPresent()) {

			Cartao novoCartao = new Cartao();
			novoCartao.setNumeroCartao(cartao.getNumeroCartao());
			novoCartao.setSenha(cartao.getSenha());
			novoCartao.setDtUpdate(new Date());
			novoCartao.setSaldo(new BigDecimal("500.00"));
			cartaoRepository.save(novoCartao);

			return ResponseEntity.status(201).body(novoCartao);
		} else {

			return ResponseEntity.status(422).body(optCartao.get());
		}

	}

	public ResponseEntity<Cartao> getSaldo(String cartao) {

		Optional<Cartao> optCartao = getCartao(cartao);

		return optCartao.isPresent() ? ResponseEntity.status(200).body(optCartao.get())
				: ResponseEntity.status(404).build();
	}

	public Optional<Cartao> getCartao(String cartao) {

		return cartaoRepository.findById(cartao);

	}

}
