package com.alura.api.challenge.receita;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{
	
	@Query(value = "SELECT COUNT(1) > 0 FROM Receita r WHERE r.descricao = :descricao AND MONTH(data) = :mes")
	Boolean temDuplicacaoDeDescricaoNoMesmoMes(String descricao, int mes);

}
