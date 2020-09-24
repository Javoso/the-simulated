package br.com.simulado.service.exception;

import java.io.Serializable;

public class CampoObrigatorioException extends RuntimeException implements Serializable {
	 
	private static final long serialVersionUID = -8922605396441669876L;

	public CampoObrigatorioException(String msg){
		super(msg);
	}

}
