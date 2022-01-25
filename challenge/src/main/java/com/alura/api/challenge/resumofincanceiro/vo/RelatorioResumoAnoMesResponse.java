package com.alura.api.challenge.resumofincanceiro.vo;

import java.math.BigDecimal;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatorioResumoAnoMesResponse {

	@JsonProperty("total_receitas")
	private BigDecimal totalReceitas;
	
	@JsonProperty("total_despesas")
	private BigDecimal totalDespesas;
	
	@JsonProperty("valor_total")
	private BigDecimal valorTotal;

	public RelatorioResumoAnoMesResponse(BigDecimal totalDespesas, BigDecimal totalReceitas, BigDecimal valorTotal) {
		Assert.notNull(totalDespesas, "O total de despesas nao pode ter valor nulo");
		Assert.notNull(totalReceitas, "O total de receitas nao pode ter valor nulo");
		this.totalReceitas = totalReceitas;
		this.totalDespesas = totalDespesas;
		this.valorTotal = valorTotal;
	}

	public BigDecimal getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(BigDecimal totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public BigDecimal getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(BigDecimal totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
