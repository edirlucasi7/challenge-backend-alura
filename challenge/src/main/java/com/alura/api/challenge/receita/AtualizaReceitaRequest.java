package com.alura.api.challenge.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AtualizaReceitaRequest {
	
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

	public AtualizaReceitaRequest(@NotBlank String descricao, @NotNull BigDecimal valor, @NotNull LocalDate data) {
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
	
	public Receita atualiza(Long id, ReceitaRepository receitaRepository) {
		Assert.isTrue(id!=null, "O id não pode ser nulo!");
		Assert.notNull(receitaRepository, "Não pode faltar a camada que conversa com o banco diretamente!");
		Receita receita = receitaRepository.findById(id).get();
		
		receita.setDescricao(this.descricao);
		receita.setValor(this.valor);
		receita.setData(this.data);
		
		receitaRepository.save(receita);
		
		return receita;
	}
}
