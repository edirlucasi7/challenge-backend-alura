package com.alura.api.challenge.receita;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.api.challenge.receita.vo.RelatorioDeReceitasPorAnoMesVO;

@RestController
public class ReceitasController {
	
	private final ReceitaRepository receitaRepository;
	
	public ReceitasController(ReceitaRepository receitaRepository) {
		this.receitaRepository = receitaRepository;
	}

	@PostMapping("/receitas")
	@Transactional
	public ResponseEntity<ReceitaResponse> cadastrar(@Valid @RequestBody NovaReceitaRequest request, UriComponentsBuilder uriComponentsBuilder) {
		if(!receitaRepository.temDuplicacaoDeDescricaoNoMesmoAnoEMes(request.getDescricao(), request.getData().getYear(), request.getData().getMonthValue())) {
			Receita receita = request.toModel();
			receitaRepository.save(receita);
			URI uri = uriComponentsBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
			return ResponseEntity.created(uri).body(new ReceitaResponse(receita));			
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/receitas")
	public ResponseEntity<Page<ReceitaResponse>> detalhar(@RequestParam(required = false) String descricao, @PageableDefault(sort = "id",
	        direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		if(descricao == null) {
			Page<Receita> receitas = receitaRepository.findAll(paginacao);
			return ResponseEntity.ok(ReceitaResponse.converte(receitas));	
		}
		Page<Receita> receitasFiltradas = receitaRepository.findByDescricaoContainingIgnoreCase(descricao, paginacao);
		return ResponseEntity.ok(ReceitaResponse.converte(receitasFiltradas));
	}
	
	@GetMapping("/receitas/{id}")
	public ResponseEntity<ReceitaResponse> detalharPorId(@PathVariable Long id) {
		Optional<Receita> optionalReceita = receitaRepository.findById(id);
		if(optionalReceita.isPresent()) {
			return ResponseEntity.ok(new ReceitaResponse(optionalReceita.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/receitas/{ano}/{mes}")
	public ResponseEntity<List<RelatorioDeReceitasPorAnoMesVO>> detalharPorAnoMes(@PathVariable("ano") Integer ano, @PathVariable("mes") Integer mes) {
		List<RelatorioDeReceitasPorAnoMesVO> relatorioDespesasPorAnoMes = receitaRepository.findByAnoMes(ano, mes);
		if(!relatorioDespesasPorAnoMes.isEmpty()) {
			return ResponseEntity.ok(relatorioDespesasPorAnoMes);		
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/receitas/{id}")
	@Transactional
	public ResponseEntity<ReceitaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizaReceitaRequest request) {
		Optional<Receita> optionalReceita = receitaRepository.findById(id);
		if(optionalReceita.isPresent()) {
			request.atualiza(id, receitaRepository);
			return ResponseEntity.ok(new ReceitaResponse(optionalReceita.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/receitas/{id}")
	@Transactional
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Optional<Receita> optionalReceita = receitaRepository.findById(id);
		if(optionalReceita.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
