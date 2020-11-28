package br.com.simulado.service;

import java.io.Serializable;

import br.com.simulado.email.ConfiguracaoEmail;

public interface ConfiguracaoEmailService extends Serializable {
	
	/**
	 * 
	 * @return
	 */
	public ConfiguracaoEmail getConfiguracao();

	/**
	 * 
	 * @param email
	 */
	public void saveOrUpdate(ConfiguracaoEmail email);

}
