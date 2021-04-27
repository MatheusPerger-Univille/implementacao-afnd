package br.com.univille.itc.afnd;

public enum Resultado {

	REJEITADO("Rejeitado"),
	ACEITO("Aceito");
	
	private String descricao;

	private Resultado(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
