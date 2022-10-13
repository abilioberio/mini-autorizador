package br.com.vr.miniautorizador.api.domain.exception;

public class TransacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TransacaoException(String message) {
		super(message);
	}

}
