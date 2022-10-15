package br.com.vr.miniautorizador.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public int debitar(Transacao transacao) {

		Optional<Cartao> optCartao = cartaoService.getCartao(transacao.getNumeroCartao());
		
		System.out.println("passou");

		if (optCartao.isPresent()) {

			Cartao cartao = optCartao.get();

			if (!isAutenticado(transacao.getSenhaCartao(), cartao.getSenha())) {
				return 2;
//				throw new TransacaoException("Senha inválida.");
			}

			BigDecimal saldoAtualizado = calcNovoSaldo(cartao.getSaldo(), transacao.getValor());

			if (saldoAtualizado.compareTo(new BigDecimal("0.00")) == -1) {
				return 3;
//				throw new TransacaoException("Saldo insuficente.");
			}

			cartao.setSaldo(saldoAtualizado);
			cartaoRepository.save(cartao);
			
			return 0;
 
		} else {
			return 1;
//			throw new TransacaoException("Cartão inexistente.");
		}

	}

	public Boolean isAutenticado(String senhaTransacao, String senhaCartao) {

		return senhaCartao.equals(senhaTransacao);
	}

	public BigDecimal calcNovoSaldo(BigDecimal saldoAtual, BigDecimal valor) {
		return saldoAtual.subtract(valor);
	}
}
