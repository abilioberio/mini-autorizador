package br.com.vr.miniautorizador.api.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transacao {

	private String numeroCartao;
	
	private String senhaCartao;
	
	private BigDecimal valor; 
	
}
