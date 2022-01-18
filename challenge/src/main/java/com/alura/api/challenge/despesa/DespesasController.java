package com.alura.api.challenge.despesa;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DespesasController {

	private final DespesaRepository despesaRepository;
	
	public DespesasController(DespesaRepository despesaRepository) {
		this.despesaRepository = despesaRepository;
	}

	@PostMapping("/despesas")
	@Transactional
	public ResponseEntity<DespesaResponse> cadastrar(@Valid @RequestBody NovaDespesaRequest request, UriComponentsBuilder uriComponentsBuilder) {
		if(!despesaRepository.verificaDuplicacaoDeDescricaoNoMesmoMes(request.getDescricao(), request.getData().getMonthValue())) {
			Despesa despesa = request.toModel();
			despesaRepository.save(despesa);
			URI uri = uriComponentsBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
			return ResponseEntity.created(uri).body(new DespesaResponse(despesa));			
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/despesas")
	public ResponseEntity<List<DespesaResponse>> detalharTodos() {
		List<Despesa> receitas = despesaRepository.findAll();
		return ResponseEntity.ok(DespesaResponse.converte(receitas));
	}
	
	@GetMapping("/despesas/{id}")
	public ResponseEntity<DespesaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizaDespesaRequest request) {
		Optional<Despesa> optionalDespesa = despesaRepository.findById(id);
		if(optionalDespesa.isPresent()) {
			request.atualiza(id, despesaRepository);
			return ResponseEntity.ok(new DespesaResponse(optionalDespesa.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/despesas/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Optional<Despesa> optionalDespesa = despesaRepository.findById(id);
		if(optionalDespesa.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
