package com.alura.api.challenge;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import jdk.jfr.Label;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import net.jqwik.time.api.Dates;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class DespesasControllerTest {

	@Autowired
	private CustomMockMvc mvc;

	@Property(tries = 5)
	@Label("fluxo de sucesso de um cadastro de despesa")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste1(@ForAll @AlphaChars @StringLength(min = 1, max = 100) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {
	
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		mvc.post("/despesas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}
	
	@Property(tries = 5)
	@Label("não deve dacastrar uma despesa com descricoes iguais dentro do mesmo mes")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste2(@ForAll @AlphaChars @StringLength(min = 1, max = 100) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {
		
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		mvc.post("/despesas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		mvc.post("/despesas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}
	
	@Test
	@DisplayName("fluxo de sucesso de exibição de detalhes de uma despesa")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste3() throws Exception {
		
		String descricao = "gas";
		BigDecimal valor = new BigDecimal("100");
		LocalDate data = LocalDate.now();
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/despesas", Map.of("descricao",descricao, "valor",valor, "data",dataFormatada));
		
		ResultActions resultado = mvc.get("/despesas/1");
		
		Map<String, Object> detalheDespesa = Map.of("descricao",descricao, "valor",valor, "data",dataFormatada);
		
		String jsonEsperado = new ObjectMapper()
				.writeValueAsString(detalheDespesa);
		
		resultado.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
		
	}

	@Provide
	Arbitrary<LocalDate> datasPresenteOuFuturas() {
		return Dates.dates().atTheEarliest(LocalDate.now().plusDays(0));
	}
}
