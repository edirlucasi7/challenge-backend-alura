package com.alura.api.challenge.receita;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ReceitaController {
	
	@Autowired
	private ReceitaRepository receitaRepository;

	@PostMapping("/receitas")
	@Transactional
	public ResponseEntity<ReceitaResponse> cadastrar(@Valid @RequestBody NovaReceitaRequest request, UriComponentsBuilder uriComponentsBuilder) {
		if(!receitaRepository.verificaDuplicacaoDeDescricaoNoMesmoMes(request.getDescricao(), request.getData().getMonthValue())) {
			Receita receita = request.toModel();
			receitaRepository.save(receita);
			URI uri = uriComponentsBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReceitaResponse(receita));			
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/receitas")
	public ResponseEntity<List<ReceitaResponse>> detalharTodos() {
		List<Receita> receitas = receitaRepository.findAll();
		return ResponseEntity.ok(ReceitaResponse.converte(receitas));
	}
	
	@GetMapping("/receitas/{id}")
	public ResponseEntity<ReceitaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizaReceitaRequest request) {
		Optional<Receita> optionalReceita = receitaRepository.findById(id);
		if(optionalReceita.isPresent()) {
			request.atualiza(id, receitaRepository);
			return ResponseEntity.ok(new ReceitaResponse(optionalReceita.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
}
