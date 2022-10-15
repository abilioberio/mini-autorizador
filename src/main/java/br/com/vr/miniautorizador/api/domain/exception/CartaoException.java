package br.com.vr.miniautorizador.api.domain.exception;

public class CartaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CartaoException(String message) {
		super(message);
	}

}
