package br.com.vr.miniautorizador.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.api.domain.model.Transacao;
import br.com.vr.miniautorizador.api.service.TransacaoService;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {
	
	@Autowired
	private TransacaoService transacaoService;
	
	@PostMapping
	public ResponseEntity<String> debito(@RequestBody Transacao transacao) {
		transacaoService.debitar(transacao);
		return ResponseEntity.status(422).body("Ok");
	}
}
