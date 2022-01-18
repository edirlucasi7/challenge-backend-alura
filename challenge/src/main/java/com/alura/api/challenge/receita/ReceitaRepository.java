package com.alura.api.challenge.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	@Query(value = "SELECT EXISTS(SELECT 1 FROM Receita r WHERE r.descricao = :descricao AND MONTH(data) = :mes)", nativeQuery = true)
	Boolean verificaDuplicacaoDeDescricaoNoMesmoMes(String descricao, int mes);

}
