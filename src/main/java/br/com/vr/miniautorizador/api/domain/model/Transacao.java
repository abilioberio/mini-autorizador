package br.com.vr.miniautorizador.api.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Transacao() {
		super();
	}	

	public Transacao(String numeroCartao, String senhaCartao, BigDecimal valor, String mensagem, int codMensagem) {
		super();
		this.numeroCartao = numeroCartao;
		this.senhaCartao = senhaCartao;
		this.valor = valor;
		this.mensagem = mensagem;
		this.codMensagem = codMensagem;
	}

	private String numeroCartao;
	
	private String senhaCartao;
	
	private BigDecimal valor; 
	
	private String mensagem;
	
	private int codMensagem;
	
}
