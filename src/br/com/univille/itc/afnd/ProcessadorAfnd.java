package br.com.univille.itc.afnd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProcessadorAfnd {

	public static final String CARACTERE_VAZIO = "*";
	public static final int LINHA_ESTADOS = 1;
	public static final int LINHA_ALFABETOS = 2;

	public static void iniciarProcesso() {
		DadosAfnd dados = obterRegras();
		List<String> entradas = Arrays.asList("0", "1");
		executar(dados, entradas);
	}

	private static DadosAfnd obterRegras() {
		DadosAfnd dados = new DadosAfnd();
		/*
		try {
			int linha = 1;
			Scanner in = new Scanner(new FileReader("D:\\Programação\\workspace-java\\trabalho_afnd_1\\resource\\automato.txt"));
			while (in.hasNextLine()) {
			    String line = in.nextLine();
			    System.out.println(line);
			    linha++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		

		List<String> estados = Arrays.asList("q1", "q2", "q3", "q4");
		dados.setEstados(estados);

		List<String> alfabeto = Arrays.asList("0", "1");
		dados.setAlfabeto(alfabeto);

		String[][] valores = new String[4][3];
		valores[0][0] = "q1";
		valores[0][1] = "q1 q2";
		valores[0][2] = "*";
		valores[1][0] = "q3";
		valores[1][1] = "*";
		valores[1][2] = "q3";
		valores[2][0] = "*";
		valores[2][1] = "q4";
		valores[2][2] = "*";
		valores[3][0] = "q4";
		valores[3][1] = "q4";
		valores[3][2] = "*";
		dados.setValores(valores);

		String estadoInicial = "q1";
		dados.setEstadoInicial(estadoInicial);

		List<String> estadoFinal = Arrays.asList("q4");
		dados.setEstadoFinal(estadoFinal);

		return dados;
	}

	private static void executar(DadosAfnd automato, List<String> entradas) {

		String estadoAtual = automato.getEstadoInicial();
		
		System.out.println("Estado inicial -> " + estadoAtual);
		
		for (String entrada : entradas) {
			
			System.out.println("Símbolo lido -> " + entrada);
			
			int index = automato.getEstados().indexOf(estadoAtual);
			String estado = automato.getValores()[index][Integer.parseInt(entrada)];
			System.out.println("Estados correntes -> " + estado);
			estadoAtual = estado;
			
		}
	}
}
