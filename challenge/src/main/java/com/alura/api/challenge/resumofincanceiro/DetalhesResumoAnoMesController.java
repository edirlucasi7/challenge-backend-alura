package com.alura.api.challenge.resumofincanceiro;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alura.api.challenge.despesa.DespesaRepository;
import com.alura.api.challenge.receita.ReceitaRepository;
import com.alura.api.challenge.resumofincanceiro.vo.RelatorioResumoAnoMesVO;
import com.alura.api.challenge.resumofincanceiro.vo.TotalDeCadaCategoriaVO;

@RestController
public class DetalhesResumoAnoMesController {

	private final DespesaRepository despesaRepository;
	private final ReceitaRepository receitaResository;

	public DetalhesResumoAnoMesController(DespesaRepository despesaRepository, ReceitaRepository receitaResository) {
		this.despesaRepository = despesaRepository;
		this.receitaResository = receitaResository;
	}

	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<RelatorioResumoAnoMesVO> gerar(@PathVariable("ano") int ano, @PathVariable("mes") int mes) {
		BigDecimal totalDespesasNoMes = despesaRepository.findTotalAnoMes(ano, mes);
		BigDecimal totalReceitasNoMes = receitaResository.findTotalAnoMes(ano, mes);

		List<TotalDeCadaCategoriaVO> totalPorCategoria = obtemValorTotalPorCategoriaNoMesmoMes(ano, mes);
		
		return ResponseEntity.ok(new RelatorioResumoAnoMesVO(totalDespesasNoMes, totalReceitasNoMes,
				totalReceitasNoMes.subtract(totalDespesasNoMes), totalPorCategoria));
	}

	private List<TotalDeCadaCategoriaVO> obtemValorTotalPorCategoriaNoMesmoMes(int ano, int mes) {
		return despesaRepository.getTotalPorCategoriaEMes(ano, mes);
	}

}
