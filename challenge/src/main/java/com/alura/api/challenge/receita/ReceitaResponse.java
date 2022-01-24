package com.alura.api.challenge.receita;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;

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

	public static Page<ReceitaResponse> converte(Page<Receita> receitas) {
		return receitas.map(ReceitaResponse::new);
	}
}
