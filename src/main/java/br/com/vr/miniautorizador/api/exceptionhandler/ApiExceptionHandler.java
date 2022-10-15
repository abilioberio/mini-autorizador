package br.com.vr.miniautorizador.api.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.vr.miniautorizador.api.domain.exception.TransacaoException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema();
		problema.setDescricao("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema.getDescricao(), headers, status, request);
	}
	
	@ExceptionHandler(TransacaoException.class)
	public ResponseEntity<Object> handleNegocio(TransacaoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		Problema problema = new Problema();
		problema.setDescricao(ex.getMessage());
		
		return handleExceptionInternal(ex, problema.getDescricao(), new HttpHeaders(), status, request);
	}
	
}
