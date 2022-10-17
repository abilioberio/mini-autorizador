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
	
	public Transacao debito(Transacao transacao) {

		Optional<Cartao> optCartao = cartaoService.getCartao(transacao.getNumeroCartao());

		if (optCartao.isPresent()) {

			Cartao cartao = optCartao.get();

			if (!isAutenticado(transacao.getSenhaCartao(), cartao.getSenha())) {
				transacao.setCodMensagem(2);
				transacao.setMensagem("Senha inválida.");
				return transacao;
			}

			BigDecimal saldoAtualizado = calcNovoSaldo(cartao.getSaldo(), transacao.getValor());

			if (saldoAtualizado.compareTo(new BigDecimal("0.00")) == -1) {
				transacao.setCodMensagem(2);
				transacao.setMensagem("Saldo insuficente.");
				return transacao;
			}

			cartao.setSaldo(saldoAtualizado);
			cartaoRepository.save(cartao);
			
			transacao.setCodMensagem(0);
			transacao.setMensagem("OK");
			
			return transacao;
 
		} else {
			
			transacao.setCodMensagem(3);
			transacao.setMensagem("Cartão inexistente.");
			
			return transacao;
		}

	}

	private Boolean isAutenticado(String senhaTransacao, String senhaCartao) {
		return senhaCartao.equals(senhaTransacao);
	}

	private BigDecimal calcNovoSaldo(BigDecimal saldoAtual, BigDecimal valor) {
		return saldoAtual.subtract(valor);
	}
}
