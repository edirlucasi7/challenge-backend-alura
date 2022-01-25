package com.alura.api.challenge;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alura.api.challenge.despesa.Categoria;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class DetalhesResumoAnoMesControllerTest {

	@Autowired
	private CustomMockMvc mvc;
	
	@Test
	@DisplayName("exibe detalhes do resumo do mes corretamente")
	void teste() throws Exception {
		
		String descricaoD = "gas";
		BigDecimal valorD = new BigDecimal("100.00");
		LocalDate dataD = LocalDate.now();
		String dataFormatadaD = dataD.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Categoria categoriaD = Categoria.ALIMENTACAO;
		
		String descricaoD2 = "roupa";
		BigDecimal valorD2 = new BigDecimal("100.00");
		LocalDate dataD2 = LocalDate.now();
		String dataFormatadaD2 = dataD2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/despesas", Map.of("descricao", descricaoD, "valor", valorD, "data", dataFormatadaD, "categoria", categoriaD))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		mvc.post("/despesas", Map.of("descricao", descricaoD2, "valor", valorD2, "data", dataFormatadaD2))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		String descricaoR = "aluguel";
		BigDecimal valorR = new BigDecimal("800.00");
		LocalDate dataR = LocalDate.now();
		String dataFormatadaR = dataR.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		mvc.post("/receitas", Map.of("descricao", descricaoR, "valor", valorR, "data", dataFormatadaR))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		ResultActions resultado = mvc.get("/resumo/"+LocalDate.now().getYear()+"/"+LocalDate.now().getMonthValue());
		
		List<Map<String, Object>> totalDeCadaCategoria = List.of(Map.of("descricao", "ALIMENTACAO", "valor_total", new BigDecimal("100.00")),
				Map.of("descricao", "OUTRAS", "valor_total", new BigDecimal("100.00")));
		
		Map<String, Object> resumoNoMes = Map.of("categoria", totalDeCadaCategoria, 
				"total_receitas", new BigDecimal("800.00"), 
				"total_despesas", new BigDecimal("200.00"),
				"saldo_total", new BigDecimal("600.00"));
		
		String jsonEsperado = new ObjectMapper()
				.writeValueAsString(resumoNoMes);
		
		resultado.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));
		
	}
	
}
