package br.com.simulado.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.TentativaFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.Simulado_;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Tentativa_;
import br.com.simulado.models.Usuario;

public class TentativaDao implements DAO<Tentativa>, Serializable {

	private static final long serialVersionUID = 1524523240902539772L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		this.builder = this.manager.getCriteriaBuilder();
	}

	@Override
	@Transactional
	public Tentativa merge(Tentativa alternativa) {
		return this.manager.merge(alternativa);
	}

	@Override
	@Transactional
	public void save(Tentativa alternativa) {
		this.manager.persist(alternativa);
	}

	@Override
	public List<Tentativa> todas() {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		query.orderBy(builder.asc(root.get(Tentativa_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Tentativa> pesquisar(Filter filtro) {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Tentativa_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Tentativa> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Tentativa> criteria = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = criteria.from(Tentativa.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Tentativa_.id)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Tentativa alternativa) {
		CriteriaUpdate<Tentativa> criteria = builder.createCriteriaUpdate(Tentativa.class);
		Root<Tentativa> root = criteria.from(Tentativa.class);
		criteria.set(root.get(Tentativa_.status), !alternativa.isAtivo()).where(builder.equal(root, alternativa));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Tentativa> root) {
		TentativaFilter filtro = (TentativaFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Tentativa_.status), filtro.isStatus()));

		if (filtro.temEstudante())
			predicates.add(builder.equal(root.get(Tentativa_.estudante), filtro.getEstudante()));

		if (filtro.temSimulado())
			predicates.add(builder.equal(root.get(Tentativa_.simulado), filtro.getSimulado()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Tentativa findById(Long id) {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		query.where(builder.equal(root.get(Tentativa_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	public Tentativa findByEstudante(Usuario estudante) {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		query.where(builder.equal(root.get(Tentativa_.estudante), estudante));

		return this.manager.createQuery(query).getSingleResult();
	}

	public List<Tentativa> findBySimulado(Simulado simulado) {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		query.where(builder.equal(root.get(Tentativa_.simulado), simulado));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Tentativa> root = criteria.from(Tentativa.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

	public List<Tentativa> findBySimulado(Long id) {
		CriteriaQuery<Tentativa> query = builder.createQuery(Tentativa.class);
		Root<Tentativa> root = query.from(Tentativa.class);
		query.where(builder.equal(root.get(Tentativa_.simulado).get(Simulado_.id), id));
		return this.manager.createQuery(query).getResultList();
	}

}
