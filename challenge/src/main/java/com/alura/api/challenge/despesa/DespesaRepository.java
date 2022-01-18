package com.alura.api.challenge.despesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DespesaRepository extends JpaRepository<Despesa, Long>{
	
	@Query(value = "SELECT EXISTS(SELECT 1 FROM Despesa r WHERE r.descricao = :descricao AND MONTH(data) = :mes)", nativeQuery = true)
	Boolean verificaDuplicacaoDeDescricaoNoMesmoMes(String descricao, int mes);

}
