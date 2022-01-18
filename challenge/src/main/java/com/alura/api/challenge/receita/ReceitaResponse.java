package com.alura.api.challenge.receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaResponse {
	
	private String descricao;

	private BigDecimal valor;

	private LocalDate data;

	public ReceitaResponse(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = receita.getData();
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}

	public static List<ReceitaResponse> converte(List<Receita> receitas) {
		return receitas.stream().map(ReceitaResponse::new).collect(Collectors.toList());
	}
}
