package br.com.dio.picpayclone.enums;

public enum BandeiraCartao {

	VISA("Visa"), MASTERCARD("Master Card"), ELO("ELO");

	private String descricao;

	BandeiraCartao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
