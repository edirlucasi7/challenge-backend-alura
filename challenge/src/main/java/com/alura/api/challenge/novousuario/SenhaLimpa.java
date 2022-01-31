package com.alura.api.challenge.novousuario;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class SenhaLimpa {

	@NotBlank
	@Length(min = 6)
	private String senhaLimpa;

	public SenhaLimpa(@NotBlank String senhaLimpa) {
		Assert.isTrue(senhaLimpa.length() >= 6, "A senha tem no minimo 6 caracteres!");
		this.senhaLimpa = senhaLimpa;
	}

	public String hash() {
		return new BCryptPasswordEncoder().encode(senhaLimpa);
	}
	
}
