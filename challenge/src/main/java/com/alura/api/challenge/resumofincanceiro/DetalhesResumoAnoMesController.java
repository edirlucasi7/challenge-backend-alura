package com.alura.api.challenge.resumofincanceiro;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alura.api.challenge.despesa.DespesaRepository;
import com.alura.api.challenge.receita.ReceitaRepository;
import com.alura.api.challenge.resumofincanceiro.vo.RelatorioResumoAnoMesResponse;

@RestController
public class DetalhesResumoAnoMesController {
	
	private final DespesaRepository despesaRepository;
	private final ReceitaRepository receitaResository;

	public DetalhesResumoAnoMesController(DespesaRepository despesaRepository, ReceitaRepository receitaResository) {
		this.despesaRepository = despesaRepository;
		this.receitaResository = receitaResository;
	}
	
	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<RelatorioResumoAnoMesResponse> gerar(@PathVariable("ano") int ano, @PathVariable("mes") int mes) {
		BigDecimal totalDespesasNoMes = despesaRepository.findTotalAnoMes(ano, mes);
		BigDecimal totalReceitasNoMes = receitaResository.findTotalAnoMes(ano, mes);
		
		if(totalDespesasNoMes == null) {
			totalDespesasNoMes = BigDecimal.ZERO;
		}
		if(totalReceitasNoMes == null) {
			totalReceitasNoMes = BigDecimal.ZERO;
		}
		
		RelatorioResumoAnoMesResponse relatorioFinal = new RelatorioResumoAnoMesResponse(totalDespesasNoMes, 
				totalReceitasNoMes, totalDespesasNoMes.add(totalReceitasNoMes));
		
		return ResponseEntity.ok(relatorioFinal);
	}

}
