package com.alura.api.challenge.resumofincanceiro.vo;

import java.math.BigDecimal;

import com.alura.api.challenge.despesa.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalDeCadaCategoriaVO {

	@JsonProperty
	private Categoria descricao;
	
	@JsonProperty("valor_total")
	private BigDecimal valorTotal;

	public TotalDeCadaCategoriaVO(Categoria descricao, BigDecimal valorTotal) {
		this.descricao = descricao;
		this.valorTotal = valorTotal;
	}

	public Categoria getDescricao() {
		return descricao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
}
