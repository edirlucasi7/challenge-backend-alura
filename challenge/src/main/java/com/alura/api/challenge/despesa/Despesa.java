package com.alura.api.challenge.despesa;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Despesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	private String valor;
	
	private LocalDate data;

	public Despesa(String descricao, String valor, LocalDate data) {
		this.descricao = descricao;
		this.valor = valor;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}
}
