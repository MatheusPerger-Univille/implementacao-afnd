package br.com.univille.itc.afnd;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		try {
			ProcessadorAfnd.iniciarProcesso();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
