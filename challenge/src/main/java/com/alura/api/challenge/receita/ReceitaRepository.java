package com.alura.api.challenge.receita;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alura.api.challenge.receita.vo.RelatorioDeReceitasPorAnoMesVO;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	@Query(value = "SELECT COUNT(1) > 0 FROM Receita r WHERE UPPER(r.descricao) = UPPER(:descricao) AND YEAR(data) = :ano AND MONTH(data) = :mes")
	Boolean temDuplicacaoDeDescricaoNoMesmoAnoEMes(String descricao, int ano, int mes);

	Page<Receita> findByDescricaoContainingIgnoreCase(String descricao, Pageable paginacao);

	@Query(value = "SELECT new com.alura.api.challenge.receita.vo.RelatorioDeReceitasPorAnoMesVO("
			+ "r.descricao, r.valor, r.data) FROM Receita r WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	List<RelatorioDeReceitasPorAnoMesVO> findByAnoMes(int ano, int mes);
	
	@Query(value = "SELECT COALESCE(SUM(valor), 0) FROM Receita r WHERE YEAR(data) = :ano AND MONTH(data) = :mes")
	BigDecimal findTotalAnoMes(int ano, int mes);

}
