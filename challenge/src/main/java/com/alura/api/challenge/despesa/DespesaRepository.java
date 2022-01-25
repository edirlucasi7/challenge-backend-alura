package com.alura.api.challenge.despesa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.api.challenge.despesa.vo.RelatorioDeDespesasPorAnoMesVO;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	@Query(value = "SELECT COUNT(1) > 0 FROM Despesa d WHERE UPPER(d.descricao) = UPPER(:descricao) AND YEAR(data) = :ano AND MONTH(data) = :mes")
	Boolean temDuplicacaoDeDescricaoNoMesmoAnoEMes(String descricao, int ano, int mes);

	Page<Despesa> findByDescricaoContainingIgnoreCase(String descricao, Pageable paginacao);

	@Query(value = "SELECT new com.alura.api.challenge.despesa.vo.RelatorioDeDespesasPorAnoMesVO("
			+ "d.descricao, d.valor, d.data, d.categoria) FROM Despesa d WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	List<RelatorioDeDespesasPorAnoMesVO> findByAnoMes(int ano, int mes);
	
	@Query(value = "SELECT COALESCE(SUM(valor), 0) FROM Despesa d WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	BigDecimal findTotalAnoMes(int ano, int mes);
}
