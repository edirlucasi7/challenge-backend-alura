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
	
	@JsonProperty
	private Categoria categoria;

	public NovaDespesaRequest(@NotBlank String descricao, @NotBlank BigDecimal valor, Categoria categoria) {
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
	}

	public Despesa toModel() {
		Despesa novaDespesa = new Despesa(descricao, valor, data);
		if(categoria==null) {
			novaDespesa.setCategoria(Categoria.OUTRAS);
		}
		return novaDespesa;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
