package br.com.vr.miniautorizador.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.domain.model.Transacao;
import br.com.vr.miniautorizador.api.domain.repository.CartaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private CartaoService cartaoService;

	public ResponseEntity<Cartao> debitar(Transacao transacao) {

		Optional<Cartao> optCartao = cartaoService.getCartao(transacao.getNumeroCartao());

		if (optCartao.isPresent()) {

			Cartao cartao = optCartao.get();

			System.out.println(transacao.getSenhaCartao() + " " + cartao.getSenha());

			if (!isAutenticado(transacao.getSenhaCartao(), cartao.getSenha())) {
				return ResponseEntity.status(422).build();
			}

			BigDecimal saldoAtualizado = calcNovoSaldo(cartao.getSaldo(), transacao.getValor());

			if (saldoAtualizado.compareTo(new BigDecimal("0.00")) == -1) {
				return ResponseEntity.status(422).build();
			}

			cartao.setSaldo(saldoAtualizado);
			cartaoRepository.save(cartao);

			return ResponseEntity.status(201).body(cartao);

		} else {

			return ResponseEntity.status(422).build();
		}

	}

	public Boolean isAutenticado(String senhaTransacao, String senhaCartao) {

		return senhaCartao.equals(senhaTransacao);
	}

	public BigDecimal calcNovoSaldo(BigDecimal saldoAtual, BigDecimal valor) {
		return saldoAtual.subtract(valor);
	}

}
