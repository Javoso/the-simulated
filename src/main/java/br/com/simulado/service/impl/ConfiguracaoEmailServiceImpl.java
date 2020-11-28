package br.com.simulado.service.impl;

import javax.inject.Inject;

import br.com.simulado.dao.ConfiguracaoEmailDAO;
import br.com.simulado.email.ConfiguracaoEmail;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.service.ConfiguracaoEmailService;

public class ConfiguracaoEmailServiceImpl implements ConfiguracaoEmailService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ConfiguracaoEmailDAO dao;

	@Override
	public ConfiguracaoEmail getConfiguracao() {
		return dao.findConfiguracao();
	}

	@Override
	@Transactional
	public void saveOrUpdate(ConfiguracaoEmail email) {
		dao.save(email);
	}
}