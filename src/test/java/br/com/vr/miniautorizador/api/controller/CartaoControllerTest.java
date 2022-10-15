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

import br.com.vr.miniautorizador.api.domain.model.Cartao;
import br.com.vr.miniautorizador.api.service.CartaoService;
import br.com.vr.miniautorizador.api.service.TransacaoService;

@WebMvcTest
public class CartaoControllerTest {

	@Autowired
	private CartaoController cartaoController;

	@MockBean
	private ModelMapper modelMapper;
	
	@MockBean
	private CartaoService cartaoService;

	@MockBean
	private TransacaoService transacaoService;
	
	
	@BeforeEach
	public void setup() {
		standaloneSetup(this.cartaoController);
	}

	@Test
	public void deveRetornarStatus201_QuandoGravarNovoCartao() {

		when(this.cartaoService.novoCartao(new Cartao("6549873025634522","2222", null)))
			.thenReturn(new Cartao("6549873025634522","2222", new BigDecimal("500.00")));
		
		given()
			.contentType("application/json")
			.body("{\"numeroCartao\": \"6549873025634522\",\"senha\": \"2222\"}")
		.when()
			.post("/cartoes")
		.then()
			.statusCode(201)
			.log().all();
	}
	
	@Test
	public void deveRetornarStatus201_QuandoGravarNovoCartao(String cartao, String senha) {

		when(this.cartaoService.novoCartao(new Cartao(cartao,senha, null)))
			.thenReturn(new Cartao(cartao,senha, new BigDecimal("500.00")));
		
		given()
			.contentType("application/json")
			.body("{\"numeroCartao\": \"" + cartao + "\",\"senha\": \"" + senha + "\"}")
		.when()
			.post("/cartoes")
		.then()
			.statusCode(201)
			.log().all();
	}
	

	@Test
	public void deveRetornarStatus422_QuandoNovoCartaoExiste() {

		when(this.cartaoService.novoCartao(new Cartao("6549873025634503","2222", null)))
		.thenReturn(null);
	
	given()
		.contentType("application/json")
		.body("{\"numeroCartao\": \"6549873025634503\",\"senha\": \"2222\"}")
	.when()
		.post("/cartoes")
	.then()
		.statusCode(422)
		.log().all();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoCartaoExiste() {

		when(this.cartaoService.getSaldo("6549873025634501"))
		.thenReturn(new BigDecimal("399.00"));
	
	given()
		.contentType("application/json")
	.when()
		.get("/cartoes/{cartao}", "6549873025634501")
	.then()
		.statusCode(200)
		.log().all();
	}
	
	@Test
	public void deveRetornarStatus404_QuandoCartaoNaoExiste() {

		when(this.cartaoService.getSaldo("6549873025634503"))
		.thenReturn(null);
	
	given()
		.contentType("application/json")
	.when()
		.get("/cartoes/{cartao}", "6549873025634503")
	.then()
		.statusCode(404)
		.log().all();
	}		
	
}
