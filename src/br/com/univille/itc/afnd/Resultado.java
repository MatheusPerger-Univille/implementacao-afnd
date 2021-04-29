package br.com.univille.itc.afnd;

public enum Resultado {

	REJEITADO("Rejeitado", false),
	ACEITO("Aceito", true);
	
	private String descricao;
	
	private boolean valor;

	private Resultado(String descricao) {
		this.descricao = descricao;
	}
	
	private Resultado(String descricao, boolean valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public boolean isValor() {
		return valor;
	}
	
	public static Resultado getResultadoByValor(boolean valor) {
		return (valor) ? ACEITO : REJEITADO;
			
	}
	
	
}
