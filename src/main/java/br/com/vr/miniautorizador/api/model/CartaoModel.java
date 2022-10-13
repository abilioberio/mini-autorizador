package br.com.vr.miniautorizador.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoModel {

	private String numeroCartao;
	
	private String senha;
	
	private String saldo;
	
	private String status;

}
