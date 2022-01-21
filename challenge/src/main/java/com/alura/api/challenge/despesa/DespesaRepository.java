package com.alura.api.challenge.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	@Query(value = "SELECT COUNT(1) > 0 FROM Despesa d WHERE d.descricao = :descricao AND MONTH(data) = :mes")
	Boolean temDuplicacaoDeDescricaoNoMesmoMes(String descricao, int mes);

}
