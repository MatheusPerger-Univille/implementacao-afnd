package br.com.univille.itc.afnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessadorAfnd {

	public static final String CARACTERE_VAZIO = "*";
	public static final int LINHA_ESTADOS = 1;
	public static final int LINHA_ALFABETOS = 2;

	/**
	 * Método que da inicio ao processo da execução do automato
	 */
	public static void iniciarProcesso() {
		DadosAfnd dados = obterRegras();
		List<String> entradas = Arrays.asList("0", "1");
		executar(dados, entradas);
	}

	/**
	 * Método que realiza a coleta dos dados de um arquivo.
	 * @return objeto DadosAfnd com todos os dados do automato
	 */
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

	/**
	 * Método onde é realizada a execução de toda a lógica do automato.
	 * 
	 * Fluxo: Será setado o estado incial, em seguida o sistema percorrerá cada entrada realizando a lógica do automato e verificando o simbolo epson,
	 * ao fim, é validado se pelo menos um fluxo finalizou no estado final.
	 * 
	 * @param automato: informações do automato
	 * @param entradas: deve ser uma List<String> com entradas numéricas
	 */
	private static void executar(DadosAfnd automato, List<String> entradas) {

		List<String> estadosAtuais = Arrays.asList(automato.getEstadoInicial());

		System.out.println("Estado inicial -> " + automato.getEstadoInicial());

		for (String entrada : entradas) {

			System.out.println("Símbolo lido -> " + entrada);
			Set<String> estadoEntrada = new HashSet<String>();
			
			for (String estado : estadosAtuais) {
				
				// Obtem o index no array que o estado pertence (linha)
				int index = automato.getEstados().indexOf(estado);
				
				// Obtem o estado da tabela com base na entrada atual
				String estadoAtual = automato.getValores()[index][Integer.parseInt(entrada)];
				
				// Faz a quebra da string por espaços e filtra os resultados que não são vazios
				List<String> proximosEstados = Arrays.asList(estadoAtual.split(" "))
						.stream()
						.filter(v -> !v.equals(CARACTERE_VAZIO))
						.collect(Collectors.toList());
				
				estadoEntrada.addAll(proximosEstados);
				
				// Percorre cada estado selecionado para verificar se existe algum símbolo epson 
				for (String estadoEpsonEntrada : new ArrayList<String>(estadoEntrada)) {
					
					int indexEpson = automato.getEstados().indexOf(estadoEpsonEntrada);
					String estadoEpson = automato.getValores()[indexEpson][automato.getValores()[0].length - 1];
					
					// Verificar se existe o epson na tabela, se sim, ele adiciona nos estados
					if (estadoEpson != null && !estadoEpson.equals(CARACTERE_VAZIO))
						estadoEntrada.add(estadoEpson);
					
				}
				
			}
			
			System.out.println("Estados correntes -> " + String.join(" ", estadoEntrada));
			estadosAtuais = new ArrayList<String>(estadoEntrada);

		}
		
		defineResultado(automato.getEstadoFinal(), estadosAtuais);
		
	}
	
	/**
	 * Métodos responsável por definir o resultado, retornar e printar no console
	 * @param estadosFinais
	 * @param estadosAtuais
	 * @return boolean
	 */
	private static boolean defineResultado(List<String> estadosFinais, List<String> estadosAtuais) {
		boolean resultado = estadosAtuais.stream().anyMatch(v -> estadosFinais.contains(v));
		System.out.println(Resultado.getResultadoByValor(resultado));
		return resultado;
		
	}
}
