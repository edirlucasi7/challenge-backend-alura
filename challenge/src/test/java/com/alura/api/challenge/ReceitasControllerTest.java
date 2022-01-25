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
public class ReceitasControllerTest {

	@Autowired
	private CustomMockMvc mvc;

	@Property(tries = 5)
	@Label("fluxo de sucesso de um cadastro de receita")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste1(@ForAll @AlphaChars @StringLength(min = 1, max = 100) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {

		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		mvc.post("/receitas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

	}
	
	@Property(tries = 5)
	@Label("não deve dacastrar um receita com descricoes iguais dentro do mesmo mes")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste2(@ForAll @AlphaChars @StringLength(min = 1, max = 20) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {
		
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		mvc.post("/receitas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		mvc.post("/receitas", Map.of("descricao", descricao, "valor", valor, "data", dataFormatada))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}
	
	@Test
	@DisplayName("fluxo de sucesso de exibicao de detalhes de uma receita")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste3() throws Exception {
		
		String descricao = "gas";
		BigDecimal valor = new BigDecimal("100");
		LocalDate data = LocalDate.now();
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/receitas", Map.of("descricao",descricao, "valor",valor, "data",dataFormatada));
		
		ResultActions resultado = mvc.get("/receitas/1");
		
		Map<String, Object> detalheDespesa = Map.of("descricao",descricao, "valor",valor, "data",dataFormatada);
		
		String jsonEsperado = new ObjectMapper()
				.writeValueAsString(detalheDespesa);
		
		resultado.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
		
	}
	
	@Property(tries = 1)
	@Label("fluxo de sucesso de exibicao de detalhes de todas as receita")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste4(@ForAll @AlphaChars @StringLength(min = 1, max = 20) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {
		
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/receitas", Map.of("descricao",descricao, "valor",valor, "data",dataFormatada));
		
		mvc.get("/receitas").andExpect(MockMvcResultMatchers.status().is2xxSuccessful());		
	}
	
	@Property(tries = 1)
	@Label("fluxo de sucesso de exibicao de lista de receitas dentro do mesmo mes")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste5(@ForAll @AlphaChars @StringLength(min = 1, max = 20) String descricao,
			@ForAll @BigRange(min = "1", max = "100") BigDecimal valor,
			@ForAll("datasPresenteOuFuturas") LocalDate data) throws Exception {
		
		String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/receitas", Map.of("descricao",descricao, "valor",valor, "data",dataFormatada));
		
		mvc.get("/receitas/"+data.getYear()+"/"+data.getMonthValue()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());		
	}
	
	@Test
	@DisplayName("fluxo de sucesso de exibicao de lista de receitas vazia")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	void teste6() throws Exception {
				
		mvc.get("/receitas/"+2022+"/"+02).andExpect(MockMvcResultMatchers.status().isNoContent());		
	
	}

	@Provide
	Arbitrary<LocalDate> datasPresenteOuFuturas() {
		return Dates.dates().atTheEarliest(LocalDate.now().plusDays(0));
	}

}
