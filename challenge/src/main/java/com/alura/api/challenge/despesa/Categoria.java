package com.alura.api.challenge.despesa;

public enum Categoria {

	ALIMENTACAO("Alimentacao"),
	SAUDE("Saude"),
	MORADIA("Moradia"),
	TRANSPORTE("Transporte"),
	EDUCACAO("Educacao"),
	LAZER("Lazer"),
	IMPREVISTOS("Imprevistos"),
	OUTRAS("Outras");
	
	private final String descricao;

	private Categoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
