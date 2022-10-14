package br.com.vr.miniautorizador.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.model.CartaoModel;
import br.com.vr.miniautorizador.api.service.CartaoService;
import br.com.vr.miniautorizador.api.service.TransacaoService;
import io.restassured.http.ContentType;

@WebMvcTest
public class CartaoControllerTest {

	@Autowired
	private CartaoController cartaoController;
	
	@MockBean
	private CartaoService cartaoService;

	@MockBean
	private TransacaoService transacaoService;
	
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.cartaoController);
	}

	@Test
	public void deveRetornarSucesso_QuandoGravarNovoCartao() {

		Cartao cartao = new Cartao("6549873025634522","2222" , null);
		
		CartaoModel cartaoModel = this.cartaoService.toCartaoModel(cartao);
		
		when(this.cartaoService.novoCartao(cartao))
			.thenReturn(ResponseEntity.status(201).body(cartaoModel));
		
		given()
			.contentType("application/json")
			.body("{\"numeroCartao\": \"6549873025634520\",\"senha\": \"2222\"}")
		.when()
			.post("/cartoes")
		.then()
			.statusCode(200)
			.log().all();
	}

	@Test
	public void deveRetornarErro_QuandoNovoCartaoExiste() {

		given()
			.accept(ContentType.JSON)
			.body("{\"numeroCartao\": \"6549873025634521\",\r\n" + "\"senha\": \"2222\"}")
		.when()
			.post("/cartoes")
		.then()
			.log().all();
	}
	
}
