package br.com.vr.miniautorizador.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.domain.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	public Cartao novoCartao(Cartao cartao) {

		Optional<Cartao> optCartao = getCartao(cartao.getNumeroCartao());

		if (!optCartao.isPresent()) {

			Cartao novoCartao = new Cartao(cartao.getNumeroCartao(), cartao.getSenha(),new BigDecimal("500.00") );
			novoCartao.setDtUpdate(new Date());
			cartaoRepository.save(novoCartao);

			return novoCartao;
			
		} else {

			return null;
		}

	}

	public BigDecimal getSaldo(String cartao) {

		Optional<Cartao> optCartao = this.getCartao(cartao);

		return optCartao.isPresent() ? optCartao.get().getSaldo()
				: null;
	}

	public Optional<Cartao> getCartao(String cartao) {

		return cartaoRepository.findById(cartao);

	}

	public List<Cartao> getCartoes() {

		return cartaoRepository.findAll();

	}
	
	
}
