package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;

public class DespesaResponse {

	private String descricao;

	private BigDecimal valor;

	private String data;
	
	private String categoria;

	public DespesaResponse(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.data = despesa.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.categoria = despesa.getCategoria().toString();
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public static Page<DespesaResponse> converte(Page<Despesa> despesas) {
		return despesas.map(DespesaResponse::new);
	}
}
