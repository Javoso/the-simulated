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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.SimuladoFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Questao;
import br.com.simulado.models.Questao_;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.Simulado_;
import br.com.simulado.models.constantes.TipoSimulado;

public class SimuladoDao implements DAO<Simulado>, Serializable {

	private static final long serialVersionUID = -2842485713412954119L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		this.builder = this.manager.getCriteriaBuilder();
	}

	@Override
	@Transactional
	public Simulado merge(Simulado simulado) {
		return this.manager.merge(simulado);
	}

	@Override
	@Transactional
	public void save(Simulado simulado) {
		this.manager.persist(simulado);
	}

	@Override
	public List<Simulado> todas() {
		CriteriaQuery<Simulado> query = builder.createQuery(Simulado.class);
		Root<Simulado> root = query.from(Simulado.class);
		query.orderBy(builder.asc(root.get(Simulado_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Simulado> pesquisar(Filter filtro) {
		CriteriaQuery<Simulado> query = builder.createQuery(Simulado.class);
		Root<Simulado> root = query.from(Simulado.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Simulado_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Simulado> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Simulado> criteria = builder.createQuery(Simulado.class);
		Root<Simulado> root = criteria.from(Simulado.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Simulado_.nome)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Simulado simulado) {
		CriteriaUpdate<Simulado> criteria = builder.createCriteriaUpdate(Simulado.class);
		Root<Simulado> root = criteria.from(Simulado.class);
		criteria.set(root.get(Simulado_.status), !simulado.isAtivo()).where(builder.equal(root, simulado));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Simulado> root) {
		SimuladoFilter filtro = (SimuladoFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temTitulo())
			predicates.add(
					builder.like(builder.lower(root.get(Simulado_.nome)), "%" + filtro.getNome().toLowerCase() + "%"));

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Simulado_.status), filtro.isStatus()));

		if (filtro.temTipo())
			predicates.add(builder.equal(root.get(Simulado_.tipoSimulado), filtro.getTipoSimulado()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Simulado findById(Long id) {
		CriteriaQuery<Simulado> query = builder.createQuery(Simulado.class);
		Root<Simulado> root = query.from(Simulado.class);
		Join<Simulado, Questao> join = root.join(Simulado_.questoes);
		query.where(builder.equal(root.get(Simulado_.id), id)).orderBy(builder.asc(join.get(Questao_.id)));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Simulado> root = criteria.from(Simulado.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

	public List<Simulado> simuladosByTipoSimulado(TipoSimulado tipoSimulado) {
		CriteriaQuery<Simulado> criteria = builder.createQuery(Simulado.class);
		Root<Simulado> root = criteria.from(Simulado.class);
		criteria.where(builder.equal(root.get(Simulado_.tipoSimulado), tipoSimulado));
		return manager.createQuery(criteria).getResultList();
	}

	public Simulado findByNome(String nome) {
		CriteriaQuery<Simulado> criteria = builder.createQuery(Simulado.class);
		Root<Simulado> root = criteria.from(Simulado.class);
		criteria.where(builder.equal(root.get(Simulado_.nome), nome));
		return manager.createQuery(criteria).getSingleResult();
	}

}
