package br.com.vr.miniautorizador.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.miniautorizador.api.model.CartaoModel;

@SpringBootTest
@AutoConfigureMockMvc
public class CartaoControllerTest2 {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void deveRetornarStatus201_QuandoGravarNovoCartao() throws Exception {

		CartaoModel cartaoModel = new CartaoModel("6549873025634509", "2222");

		mockMvc.perform(post("/cartoes")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cartaoModel)))
				.andExpect(status().isCreated());

	}

	@Test
	public void deveRetornarStatus422_QuandoNovoCartaoExiste() throws Exception {

		CartaoModel cartaoModel = new CartaoModel("659873025634509", "2222");

		mockMvc.perform(post("/cartoes").contentType("application/json")
				.content(objectMapper.writeValueAsString(cartaoModel)))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void deveRetornarStatus200_QuandoCartaoExiste() throws Exception {

		mockMvc.perform(post("/cartoes/659873025634521")).andExpect(status().isOk());

	}

	@Test
	public void deveRetornarStatus404_QuandoCartaoNaoExiste() throws Exception {

		mockMvc.perform(get("/cartoes/659873025634521")).andExpect(status().isNotFound());

	}
}
