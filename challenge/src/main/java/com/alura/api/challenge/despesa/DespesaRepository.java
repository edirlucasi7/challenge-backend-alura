package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.api.challenge.despesa.vo.RelatorioDeDespesasPorAnoMesVO;
import com.alura.api.challenge.resumofincanceiro.vo.TotalDeCadaCategoriaVO;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	@Query(value = "SELECT COUNT(1) > 0 FROM Despesa d WHERE UPPER(d.descricao) = UPPER(:descricao) AND YEAR(data) = :ano AND MONTH(data) = :mes")
	Boolean temDuplicacaoDeDescricaoNoMesmoAnoEMes(String descricao, Integer ano, Integer mes);

	Page<Despesa> findByDescricaoContainingIgnoreCase(String descricao, Pageable paginacao);
	
	@Query(value = "SELECT new com.alura.api.challenge.despesa.vo.RelatorioDeDespesasPorAnoMesVO("
			+ "d.descricao, d.valor, d.data, d.categoria) FROM Despesa d WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	List<RelatorioDeDespesasPorAnoMesVO> findByAnoMes(Integer ano, Integer mes);
	
	@Query(value = "SELECT COALESCE(SUM(valor), 0) FROM Despesa d WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	BigDecimal findTotalAnoMes(Integer ano, Integer mes);
	
	@Query(value = "SELECT new com.alura.api.challenge.resumofincanceiro.vo.TotalDeCadaCategoriaVO("
			+ "d.categoria, COALESCE(SUM(valor), 0)) FROM Despesa d WHERE YEAR(data) = :ano AND MONTH(data) = :mes GROUP BY d.categoria")
	List<TotalDeCadaCategoriaVO> getTotalPorCategoriaEMes(Integer ano, Integer mes);
	
}
