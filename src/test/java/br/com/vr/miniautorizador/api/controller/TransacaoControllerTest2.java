package br.com.vr.miniautorizador.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.miniautorizador.api.model.TransacaoModel;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoControllerTest2 {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void deveRetornarSucesso_QuandoHouverSaldoSuficiente() throws Exception {
		
		TransacaoModel transacaoModel = new TransacaoModel("6549873025634501","2222", new BigDecimal("50.00"));
		
		mockMvc.perform(post("/transacoes")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(transacaoModel)))
			.andExpect(status().isCreated());

	}

	
}
