package br.com.univille.itc.afnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessadorAfnd {

	public static final String CARACTERE_VAZIO = "*";
	public static final int LINHA_ESTADOS = 1;
	public static final int LINHA_ALFABETOS = 2;
	
	public static final String NOME_ARQUIVO_OBTER_REGRAS = "automato.txt";
	public static final String NOME_ARQUIVO_OBTER_ENTRADAS = "entrada.txt";
	public static final String NOME_ARQUIVO_PASSOS = "passos.txt";
	public static final String NOME_ARQUIVO_RESULTADOS = "resultado.txt";

	/**
	 * Método que da inicio ao processo da execução do automato e grava ao final em um arquivo
	 * @throws IOException 
	 */
	public static void iniciarProcesso() throws IOException {
		DadosAfnd dados = obterRegras();
		
		List<List<String>> entradas = obterEntradas();
		
		apagarArquivos();
		
		for (List<String> listaEntrada : entradas) {
			List<List<String>> retornos = executar(dados, listaEntrada);
			boolean resultado = defineResultado(dados.getEstadoFinal(), retornos.get(0));
			gravarInformacoes(retornos.get(1), Resultado.getResultadoByValor(resultado));
		}
		
	}
	
	/**
	 * Método que reseta os arquivos para uma nova escrita
	 * @throws IOException
	 */
	private static void apagarArquivos() throws IOException {
		
		new FileWriter("./resource/" + NOME_ARQUIVO_PASSOS, false).close();
		new FileWriter("./resource/" + NOME_ARQUIVO_RESULTADOS, false).close();
	}
	
	/**
	 * Métodos que grava as informações geradas em cada linha de entrada em arquivos de passos e resultados
	 * @param mensagens - mensagens geradas pelo peocessamento do automato
	 * @param resultado - resultado da linha de entrada
	 * @throws IOException
	 */
	private static void gravarInformacoes(List<String> mensagens, Resultado resultado) throws IOException {
		 
		// Arquivo dos passos
		File file = new File("./resource/" + NOME_ARQUIVO_PASSOS);
		FileWriter writer = new FileWriter(file, true);
		
		for (String mensagem : mensagens) {
			writer.write(mensagem);
			writer.write(System.getProperty( "line.separator" ));
		}
		
		writer.write("--------------------------------");
		writer.write(System.getProperty( "line.separator" ));
		writer.close();

		// Arquivo de resultados
		File fileResult = new File("./resource/" + NOME_ARQUIVO_RESULTADOS);
		FileWriter writerResult = new FileWriter(fileResult, true);
		writerResult.write(resultado.name());
		writerResult.write(System.getProperty( "line.separator" ));
		writerResult.close();
		
	}
	
	/**
	 * Método que obtem a entrada a partir de um arquivo txt
	 * @return List<List<String>>
	 */
	private static List<List<String>> obterEntradas() {
		List<List<String>> entradas = new ArrayList<>();
		
		InputStream is = ProcessadorAfnd.class.getClassLoader().getResourceAsStream(NOME_ARQUIVO_OBTER_ENTRADAS);
		Scanner in = new Scanner(is);
		
		while (in.hasNextLine()) {
			List<String> values = new ArrayList<>();
			String[] valores = in.nextLine().split(" ");
			values.addAll(Arrays.asList(valores));
			entradas.add(values);
		}
		
		return entradas;
		
	}

	/**
	 * Método que realiza a coleta dos dados de um arquivo.
	 * @return objeto DadosAfnd com todos os dados do automato
	 */
	private static DadosAfnd obterRegras() {
		
		DadosAfnd dados = new DadosAfnd();
		int totalRegistrosTabela = 0;
		String[][] valores = null;
		
		try {
			int linha = 1;
			InputStream is = ProcessadorAfnd.class.getClassLoader().getResourceAsStream(NOME_ARQUIVO_OBTER_REGRAS);
			Scanner in = new Scanner(is);
			
			while (in.hasNextLine()) {
			    String linhaCompleta = in.nextLine();
			    
			    if (linha == LINHA_ESTADOS) {
			    	dados.setEstados(Arrays.asList(linhaCompleta.split(" ")));
			    	linha++;
			    	continue;
			    }
			    
			    if (linha == LINHA_ALFABETOS) {
			    	dados.setAlfabeto(Arrays.asList(linhaCompleta.split(" ")));
			    	linha++;
			    	continue;
			    }
			    
			    totalRegistrosTabela = dados.getEstados().size() * (dados.getAlfabeto().size() + 1);
			    
			    if (valores == null)
			    	valores = new String[dados.getEstados().size()][dados.getAlfabeto().size() + 1];
			    
			    if (linha <= (totalRegistrosTabela + 2)) {
			    	outerloop:
			    	for (int i = 0; i < valores.length; i++) {
			    		
			    		for (int j = 0; j < dados.getAlfabeto().size() + 1; j++) {
			    			if (valores[i][j] == null) {
			    				valores[i][j] = linhaCompleta;
			    				break outerloop;
			    			}	
			    		}
			    		
					}
			    } else if ((totalRegistrosTabela + 2) + 1 == linha) { // Determina o estado inicial
			    	dados.setValores(valores);
			    	dados.setEstadoInicial(linhaCompleta);
			    } else if ((totalRegistrosTabela + 2) + 2 == linha) { // Determina o estado final
			    	dados.setEstadoFinal(Arrays.asList(linhaCompleta.split(" ")));
			    }
			    
			    linha++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	private static List<List<String>> executar(DadosAfnd automato, List<String> entradas) {
		
		List<List<String>> retorno = new ArrayList<>();
		List<String> mensagens = new ArrayList<>();

		List<String> estadosAtuais = Arrays.asList(automato.getEstadoInicial());

		System.out.println("Estado inicial -> " + automato.getEstadoInicial());
		mensagens.add("Estado inicial -> " + automato.getEstadoInicial());

		for (String entrada : entradas) {

			System.out.println("Símbolo lido -> " + entrada);
			mensagens.add("Símbolo lido -> " + entrada);
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
			mensagens.add("Estados correntes -> " + String.join(" ", estadoEntrada));
			estadosAtuais = new ArrayList<String>(estadoEntrada);

		}
		
		retorno.add(estadosAtuais);
		retorno.add(mensagens);
		return retorno;
			
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
