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

import br.com.simulado.dao.filter.AlternativaFilter;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Alternativa_;
import br.com.simulado.models.Questao;

public class AlternativaDao implements DAO<Alternativa>, Serializable {

	private static final long serialVersionUID = -8852195364921388719L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		this.builder = this.manager.getCriteriaBuilder();
	}

	@Override
	@Transactional
	public Alternativa merge(Alternativa alternativa) {
		return this.manager.merge(alternativa);
	}

	@Override
	@Transactional
	public void save(Alternativa alternativa) {
		this.manager.persist(alternativa);
	}

	@Override
	public List<Alternativa> todas() {
		CriteriaQuery<Alternativa> query = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = query.from(Alternativa.class);
		query.orderBy(builder.asc(root.get(Alternativa_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Alternativa> pesquisar(Filter filtro) {
		CriteriaQuery<Alternativa> query = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = query.from(Alternativa.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Alternativa_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Alternativa> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Alternativa> criteria = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = criteria.from(Alternativa.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Alternativa_.id)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Alternativa alternativa) {
		CriteriaUpdate<Alternativa> criteria = builder.createCriteriaUpdate(Alternativa.class);
		Root<Alternativa> root = criteria.from(Alternativa.class);
		criteria.set(root.get(Alternativa_.status), !alternativa.isAtivo()).where(builder.equal(root, alternativa));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Alternativa> root) {
		AlternativaFilter filtro = (AlternativaFilter) filter;
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Alternativa_.status), filtro.isStatus()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Alternativa findById(Long id) {
		CriteriaQuery<Alternativa> query = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = query.from(Alternativa.class);
		query.where(builder.equal(root.get(Alternativa_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}
	
	public Alternativa findByEnunciado(String enunciado) {
		CriteriaQuery<Alternativa> query = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = query.from(Alternativa.class);
		query.where(builder.like(builder.lower(root.get(Alternativa_.enunciado)), "%" + enunciado.toLowerCase() + "%"));
		
		return this.manager.createQuery(query).getSingleResult();
	}
	
	public List<Alternativa> findByQuestao(Questao questao) {
		CriteriaQuery<Alternativa> query = builder.createQuery(Alternativa.class);
		Root<Alternativa> root = query.from(Alternativa.class);
		query.where(builder.equal(root.get(Alternativa_.questao), questao));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Alternativa> root = criteria.from(Alternativa.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

}
