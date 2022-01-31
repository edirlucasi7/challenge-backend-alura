package com.alura.api.challenge.novousuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class NovoUsuarioRequest {

	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Length(min = 6)
	private String senha;

	public NovoUsuarioRequest(@NotBlank String email, @NotBlank @Length(min = 6) String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario toModel() {
		return new Usuario(email, new SenhaLimpa(senha));
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
}
