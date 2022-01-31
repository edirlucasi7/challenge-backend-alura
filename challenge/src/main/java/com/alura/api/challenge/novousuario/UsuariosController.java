package com.alura.api.challenge.novousuario;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuariosController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProibeEmailDuplicadoValidator proibeEmailDuplicadoValidator;
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(proibeEmailDuplicadoValidator);
	}
	
	@PostMapping("/usuarios")
	@Transactional
	public String cadastrar(@Valid @RequestBody NovoUsuarioRequest request) {
		Usuario usuario = request.toModel();
		usuarioRepository.save(usuario);
		return usuario.toString();
	}
	
}
