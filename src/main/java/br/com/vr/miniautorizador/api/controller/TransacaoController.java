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
	public ResponseEntity<String> debito(@RequestBody Transacao oper) {

		this.transacaoService.debito(oper);
		
		return oper.getCodMensagem() == 0 ? ResponseEntity.status(201).body(oper.getMensagem())
				: oper.getCodMensagem() == 1 ? ResponseEntity.status(422).body(oper.getMensagem())
				: oper.getCodMensagem() == 2 ? ResponseEntity.status(422).body(oper.getMensagem())
				: ResponseEntity.status(422).body(oper.getMensagem());
	}
}
