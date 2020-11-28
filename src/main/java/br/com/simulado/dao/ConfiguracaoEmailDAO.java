package br.com.simulado.dao;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.QueryHints;

import br.com.simulado.email.ConfiguracaoEmail;

public class ConfiguracaoEmailDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient EntityManager manager;

	@Inject
	private transient Logger logger;

	/**
	 * 
	 * @param email
	 * @return
	 */
	public ConfiguracaoEmail save(ConfiguracaoEmail email) {
		return manager.merge(email);
	}

	/**
	 * 
	 * @return
	 */
	public ConfiguracaoEmail findConfiguracao() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ConfiguracaoEmail> criteria = builder.createQuery(ConfiguracaoEmail.class);
		Root<ConfiguracaoEmail> c = criteria.from(ConfiguracaoEmail.class);
		criteria.select(c);
		try {
			return manager.createQuery(criteria).setHint(QueryHints.HINT_CACHEABLE, true)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.severe("Nenhuma configuracao de email encontrada");
		}
		return null;
	}
}