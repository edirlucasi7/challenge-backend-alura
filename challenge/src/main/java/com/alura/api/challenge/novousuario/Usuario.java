package com.alura.api.challenge.novousuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String senha;
	
	public Usuario() { }

	public Usuario(@NotBlank String email, @NotBlank SenhaLimpa senhaLimpa) {
		this.email = email;
		this.senha = senhaLimpa.hash();
		
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", senha=" + senha + "]";
	}

}
