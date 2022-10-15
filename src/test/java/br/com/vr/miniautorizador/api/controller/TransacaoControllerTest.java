package br.com.vr.miniautorizador.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.vr.miniautorizador.api.domain.model.Transacao;
import br.com.vr.miniautorizador.api.service.CartaoService;
import br.com.vr.miniautorizador.api.service.TransacaoService;

@WebMvcTest
public class TransacaoControllerTest {

	@Autowired
	private TransacaoController transacaoController;

	@MockBean
	private ModelMapper modelMapper;
	
	@MockBean
	private CartaoService cartaoService;

	@MockBean
	private TransacaoService transacaoService;
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.transacaoController);
	}

	@Test
	public void deveRetornarSucesso_QuandoHouverSaldoSuficiente() {

		when(this.transacaoService.debitar(new Transacao("6549873025634501","2222", new BigDecimal("50.00"))))
		.thenReturn(0);
		
		given()
			.contentType("application/json")
			.body("{\"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"2222\", \"valor\": \"50.00\"}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.log().all();
	}
	
	@Test
	public void deveRetornarErro_QuandoNaoHouverSaldoSuficiente() {

		when(this.transacaoService.debitar(new Transacao("6549873025634501","2222", new BigDecimal("5000.00"))))
		.thenReturn(3);
		
		given()
			.contentType("application/json")
			.body("{\"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"2222\", \"valor\": \"5000.00\"}")
		.when()
			.post("/transacoes")
		.then()
			.contentType("text/plain")
			.statusCode(422)
			.log().all();
	}
	
}
