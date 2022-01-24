package com.alura.api.challenge.despesa.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alura.api.challenge.despesa.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatorioDeDespesasPorAnoMesVO {

	@JsonProperty
	private String descricao;
	
	@JsonProperty
	private BigDecimal valor;
	
	@JsonProperty
	private String data;
	
	@JsonProperty
	private Categoria categoria;

	public RelatorioDeDespesasPorAnoMesVO(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.categoria = categoria;
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

	public Categoria getCategoria() {
		return categoria;
	}
	
}
