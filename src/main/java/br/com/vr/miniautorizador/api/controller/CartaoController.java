package br.com.vr.miniautorizador.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.service.CartaoService;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;

	@PostMapping
	public ResponseEntity<Cartao> novoCartao(@RequestBody Cartao cartao) {
		return cartaoService.novoCartao(cartao);
	}

	@GetMapping("/{numeroCartao}")
	public ResponseEntity<Cartao> obterSaldo(@PathVariable String numeroCartao) {
		return cartaoService.getSaldo(numeroCartao);
	}
}
