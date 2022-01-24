package com.alura.api.challenge.receita.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatorioDeReceitasPorAnoMesVO {

	@JsonProperty
	private String descricao;
	
	@JsonProperty
	private BigDecimal valor;
	
	@JsonProperty
	private String data;

	public RelatorioDeReceitasPorAnoMesVO(String descricao, BigDecimal valor, LocalDate data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

}
