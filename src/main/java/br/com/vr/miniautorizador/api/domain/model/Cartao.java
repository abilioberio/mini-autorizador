package br.com.vr.miniautorizador.api.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "cartao")
public class Cartao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	private String numeroCartao;
	
	@Column
	private String senha;
	
	@Column
	private BigDecimal saldo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dtUpdate;

}
