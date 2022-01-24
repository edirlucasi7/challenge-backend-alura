package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AtualizaDespesaRequest {

	@NotBlank
	@JsonProperty
	private String descricao;

	@NotNull
	@JsonProperty
	private BigDecimal valor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull
	@JsonProperty
	@FutureOrPresent
	private LocalDate data;
	
	@JsonProperty
	private Categoria categoria;

	public AtualizaDespesaRequest(@NotBlank String descricao, @NotNull BigDecimal valor, @NotNull LocalDate data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
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
	
	public Categoria getCategoria() {
		return categoria;
	}

	public Despesa atualiza(Long id, DespesaRepository despesaRepository) {
		Assert.isTrue(id!=null, "O id não pode ser nulo!");
		Despesa despesa = despesaRepository.findById(id).get();
		
		despesa.setDescricao(this.descricao);
		despesa.setValor(this.valor);
		despesa.setData(this.data);
		despesa.setCategoria(this.categoria);
				
		return despesa;
	}

}
