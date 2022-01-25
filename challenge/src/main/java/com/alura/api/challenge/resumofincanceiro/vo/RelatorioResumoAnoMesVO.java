package com.alura.api.challenge.resumofincanceiro.vo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelatorioResumoAnoMesVO {

	@JsonProperty("total_receitas")
	private BigDecimal totalReceitas;
	
	@JsonProperty("total_despesas")
	private BigDecimal totalDespesas;
	
	@JsonProperty("saldo_total")
	private BigDecimal saldoTotal;
	
	@JsonProperty
	private List<TotalDeCadaCategoriaVO> categoria;

	public RelatorioResumoAnoMesVO(BigDecimal totalDespesas, BigDecimal totalReceitas, BigDecimal saldoTotal, List<TotalDeCadaCategoriaVO> categoria) {
		Assert.notNull(totalDespesas, "O total de despesas nao pode ter valor nulo");
		Assert.notNull(totalReceitas, "O total de receitas nao pode ter valor nulo");
		this.totalReceitas = totalReceitas;
		this.totalDespesas = totalDespesas;
		this.saldoTotal = saldoTotal;
		this.categoria = categoria;
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

	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	public List<TotalDeCadaCategoriaVO> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<TotalDeCadaCategoriaVO> categoria) {
		this.categoria = categoria;
	}
	
}
