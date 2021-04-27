package br.com.univille.itc.afnd;

import java.util.ArrayList;
import java.util.List;

public class DadosAfnd {

	private List<String> estados;

	private List<String> alfabeto;

	private String[][] valores;

	private String estadoInicial;

	private List<String> estadoFinal;

	public List<String> getEstados() {
		
		if (estados == null)
			estados = new ArrayList<String>();
		
		return estados;
	}

	public void setEstados(List<String> estados) {
		this.estados = estados;
	}

	public List<String> getAlfabeto() {
		
		if (alfabeto == null)
			alfabeto = new ArrayList<String>();
		
		return alfabeto;
	}

	public void setAlfabeto(List<String> alfabeto) {
		this.alfabeto = alfabeto;
	}

	public String[][] getValores() {
		
		return valores;
	}

	public void setValores(String[][] valores) {
		this.valores = valores;
	}

	public String getEstadoInicial() {
		return estadoInicial;
	}

	public void setEstadoInicial(String estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public List<String> getEstadoFinal() {
		
		if (estadoFinal == null)
			estadoFinal = new ArrayList<String>();
		
		return estadoFinal;
	}

	public void setEstadoFinal(List<String> estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

}
