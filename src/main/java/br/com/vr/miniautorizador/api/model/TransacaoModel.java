package br.com.vr.miniautorizador.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoModel {

	public TransacaoModel(String numeroCartao, String senhaCartao, BigDecimal valor) {
		super();
		this.numeroCartao = numeroCartao;
		this.senhaCartao = senhaCartao;
		this.valor = valor;
	}

	private String numeroCartao;
	
	private String senhaCartao;
	
	private BigDecimal valor; 

}
