package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DespesaResponse {

	private String descricao;

	private BigDecimal valor;

	private LocalDate data;

	public DespesaResponse(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.data = despesa.getData();
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

	public static List<DespesaResponse> converte(List<Despesa> despesas) {
		return despesas.stream().map(DespesaResponse::new).collect(Collectors.toList());
	}
}
