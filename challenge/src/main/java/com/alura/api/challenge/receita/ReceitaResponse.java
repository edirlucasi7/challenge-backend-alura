package com.alura.api.challenge.receita;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReceitaResponse {
	
	private String descricao;

	private BigDecimal valor;

	private String data;

	public ReceitaResponse(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = receita.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getData() {
		return data;
	}

	public static List<ReceitaResponse> converte(List<Receita> receitas) {
		return receitas.stream().map(ReceitaResponse::new).collect(Collectors.toList());
	}
}
