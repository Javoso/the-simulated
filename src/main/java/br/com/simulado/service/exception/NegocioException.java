package br.com.simulado.service.exception;

import java.io.Serializable;

public class NegocioException extends Exception implements Serializable {

	private static final long serialVersionUID = -2271953948484505047L;

	public NegocioException(String msg) {
		super(msg);
	}
	
}