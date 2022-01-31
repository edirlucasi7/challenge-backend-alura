package com.alura.api.challenge.despesa;

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

import com.alura.api.challenge.despesa.vo.RelatorioDeDespesasPorAnoMesVO;

@RestController
public class DespesasController {

	private final DespesaRepository despesaRepository;
	
	public DespesasController(DespesaRepository despesaRepository) {
		this.despesaRepository = despesaRepository;
	}

	@PostMapping("/despesas")
	@Transactional
	public ResponseEntity<DespesaResponse> cadastrar(@Valid @RequestBody NovaDespesaRequest request, UriComponentsBuilder uriComponentsBuilder) {
		if(!despesaRepository.temDuplicacaoDeDescricaoNoMesmoAnoEMes(request.getDescricao(), request.getData().getYear(), request.getData().getMonthValue())) {
			Despesa despesa = request.toModel();
			despesaRepository.save(despesa);
			URI uri = uriComponentsBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
			return ResponseEntity.created(uri).body(new DespesaResponse(despesa));			
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/despesas")
	public ResponseEntity<Page<DespesaResponse>> detalhar(@RequestParam(required = false) String descricao, @PageableDefault(sort = "id",
	        direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		if(descricao == null) {
			Page<Despesa> despesas = despesaRepository.findAll(paginacao);
			return ResponseEntity.ok(DespesaResponse.converte(despesas));	
		}
		Page<Despesa> despesasFiltradas = despesaRepository.findByDescricaoContainingIgnoreCase(descricao, paginacao);
		return ResponseEntity.ok(DespesaResponse.converte(despesasFiltradas));
	}
	
	@GetMapping("/despesas/{id}")
	public ResponseEntity<DespesaResponse> detalharPorId(@PathVariable Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if(despesa.isPresent()) {
			return ResponseEntity.ok().body(new DespesaResponse(despesa.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/despesas/{ano}/{mes}")
	public ResponseEntity<List<RelatorioDeDespesasPorAnoMesVO>> detalharPorAnoMes(@PathVariable("ano") Integer ano, @PathVariable("mes") Integer mes) {
		List<RelatorioDeDespesasPorAnoMesVO> relatorioDespesasPorAnoMes = despesaRepository.findByAnoMes(ano, mes);
		if(!relatorioDespesasPorAnoMes.isEmpty()) {
			return ResponseEntity.ok(relatorioDespesasPorAnoMes);		
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/despesas/{id}")
	@Transactional
	public ResponseEntity<DespesaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizaDespesaRequest request) {
		Optional<Despesa> optionalDespesa = despesaRepository.findById(id);
		if(optionalDespesa.isPresent()) {
			request.atualiza(id, despesaRepository);
			return ResponseEntity.ok(new DespesaResponse(optionalDespesa.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/despesas/{id}")
	@Transactional
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Optional<Despesa> optionalDespesa = despesaRepository.findById(id);
		if(optionalDespesa.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
