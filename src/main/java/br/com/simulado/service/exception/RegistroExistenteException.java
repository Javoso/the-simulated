package br.com.simulado.service.exception;

import java.io.Serializable;

public class RegistroExistenteException extends Exception implements Serializable {

	private static final long serialVersionUID = 3654904838733798019L;

	public RegistroExistenteException(String message) {
		super(message);
	}
	
}