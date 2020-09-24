package br.com.simulado.service.exception;

import java.io.Serializable;

public class DataInvalidaException extends RuntimeException implements Serializable {
	 
	private static final long serialVersionUID = -8922605396441669876L;

	public DataInvalidaException(String msg){
		super(msg);
	}

}