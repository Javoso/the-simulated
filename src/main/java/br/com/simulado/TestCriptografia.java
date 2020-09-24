package br.com.simulado;

import br.com.simulado.util.AES;

public class TestCriptografia {

	public static void main(String[] args) {
		AES AES = new AES();

		String textoNormal = "Lucas Queiroz O Javoso";
		Long id = 2038L;
		String textoCriptografado = "";
		String textoDescriptografado = "";
		try {
			textoCriptografado = AES.codificar(id.toString());
			textoDescriptografado = AES.decodificar(textoCriptografado);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Texto Normal: " + id);
		System.out.println();
		System.out.println("Texto Criptografado: " + textoCriptografado);
		System.out.println();
		System.out.println("Texto Descriptografado: " + textoDescriptografado);
		System.out.println();
	}

}
