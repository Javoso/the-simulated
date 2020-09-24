package br.com.simulado.infra.jpa;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	private EntityManagerFactory simulado;

	public EntityManagerProducer() {
		simulado = Persistence.createEntityManagerFactory("simulado");
	}
	
//	@PersistenceUnit(unitName = "simulado")
//	private EntityManagerFactory simulado;

	@Produces
	@RequestScoped
	public EntityManager createNotificadorPagamento() {
		return simulado.createEntityManager();
	}

	public void close(@Disposes @Any EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}
}