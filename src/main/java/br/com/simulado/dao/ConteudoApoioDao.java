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

import br.com.simulado.dao.filter.ConteudoApoioFilter;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.ConteudoApoio;
import br.com.simulado.models.ConteudoApoio_;
import br.com.simulado.models.SubCategoria;

public class ConteudoApoioDao implements DAO<ConteudoApoio>, Serializable {

	private static final long serialVersionUID = -8852195364921388719L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		builder = this.manager.getCriteriaBuilder();
	}
	
	@Override
	@Transactional
	public ConteudoApoio merge(ConteudoApoio conteudoApoio) {
		return this.manager.merge(conteudoApoio);
	}

	@Override
	@Transactional
	public void save(ConteudoApoio conteudoApoio) {
		this.manager.persist(conteudoApoio);
	}

	@Override
	public List<ConteudoApoio> todas() {
		CriteriaQuery<ConteudoApoio> query = builder.createQuery(ConteudoApoio.class);
		Root<ConteudoApoio> root = query.from(ConteudoApoio.class);
		query.orderBy(builder.asc(root.get(ConteudoApoio_.titulo)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<ConteudoApoio> pesquisar(Filter filtro) {
		CriteriaQuery<ConteudoApoio> query = builder.createQuery(ConteudoApoio.class);
		Root<ConteudoApoio> root = query.from(ConteudoApoio.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(ConteudoApoio_.titulo)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<ConteudoApoio> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<ConteudoApoio> criteria = builder.createQuery(ConteudoApoio.class);
		Root<ConteudoApoio> root = criteria.from(ConteudoApoio.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(ConteudoApoio_.titulo)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(ConteudoApoio conteudoApoio) {
		CriteriaUpdate<ConteudoApoio> criteria = builder.createCriteriaUpdate(ConteudoApoio.class);
		Root<ConteudoApoio> root = criteria.from(ConteudoApoio.class);
		criteria.set(root.get(ConteudoApoio_.status), !conteudoApoio.isAtivo()).where(builder.equal(root, conteudoApoio));
		this.manager.createQuery(criteria).executeUpdate();
	}
	
	@Override
	public ConteudoApoio findById(Long id) {
		CriteriaQuery<ConteudoApoio> query = builder.createQuery(ConteudoApoio.class);
		Root<ConteudoApoio> root = query.from(ConteudoApoio.class);
		query.where(builder.equal(root.get(ConteudoApoio_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<ConteudoApoio> root) {
		
		ConteudoApoioFilter filtro = (ConteudoApoioFilter) filter;

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temTitulo())
			predicates.add(
					builder.like(builder.lower(root.get(ConteudoApoio_.titulo)), "%" + filtro.getTitulo().toLowerCase() + "%"));

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(ConteudoApoio_.status), filtro.isStatus()));
			
		if (filtro.temSubCategoria())
			predicates.add(builder.equal(root.get(ConteudoApoio_.subCategoria), filtro.getSubCategoria()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public List<ConteudoApoio> findBySubCategoria(SubCategoria subCategoria) {
		CriteriaQuery<ConteudoApoio> criteriaQuery = builder.createQuery(ConteudoApoio.class);
		Root<ConteudoApoio> root = criteriaQuery.from(ConteudoApoio.class);
		criteriaQuery.where(builder.equal(root.get(ConteudoApoio_.subCategoria), subCategoria));
		criteriaQuery.orderBy(builder.asc(root.get(ConteudoApoio_.id)));
		return manager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ConteudoApoio> root = criteria.from(ConteudoApoio.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

}
