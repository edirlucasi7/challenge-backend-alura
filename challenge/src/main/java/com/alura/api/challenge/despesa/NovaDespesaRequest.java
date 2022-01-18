package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NovaDespesaRequest {

	@NotBlank
	@JsonProperty
	private String descricao;

	@NotNull
	@JsonProperty
	private BigDecimal valor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@JsonProperty
	private LocalDate data;

	public NovaDespesaRequest(@NotBlank String descricao, @NotBlank BigDecimal valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public Despesa toModel() {
		return new Despesa(descricao, valor, data);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}
}
