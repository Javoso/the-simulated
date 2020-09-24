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
import br.com.simulado.dao.filter.QuestaoFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Questao;
import br.com.simulado.models.Questao_;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.models.SubCategoria_;

public class QuestaoDao implements DAO<Questao>, Serializable {

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
	public Questao merge(Questao questao) {
		return this.manager.merge(questao);
	}

	@Override
	@Transactional
	public void save(Questao questao) {
		this.manager.persist(questao);
	}

	@Override
	public List<Questao> todas() {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
//		Join<Questao, Alternativa> join = root.join(Questao_.alternativas, JoinType.LEFT);
//		query.where(builder.equal(root.get(Questao_.id), join.get(Alternativa_.questao).get(Questao_.id)));
		query.orderBy(builder.asc(root.get(Questao_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Questao> pesquisar(Filter filtro) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Questao_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Questao> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Questao> criteria = builder.createQuery(Questao.class);
		Root<Questao> root = criteria.from(Questao.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Questao_.id)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Questao questao) {
		CriteriaUpdate<Questao> criteria = builder.createCriteriaUpdate(Questao.class);
		Root<Questao> root = criteria.from(Questao.class);
		criteria.set(root.get(Questao_.status), !questao.isAtivo()).where(builder.equal(root, questao));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Questao> root) {
		QuestaoFilter filtro = (QuestaoFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Questao_.status), filtro.isStatus()));
		if (filtro.temSubCategoria())
			predicates.add(builder.equal(root.get(Questao_.subCategoria), filtro.getSubCategoria()));
		if (filtro.temTitulo())
			builder.like(builder.lower(root.get(Questao_.enunciado)), "%" + filtro.getEnunciado().toLowerCase() + "%");

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Questao findById(Long id) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		query.where(builder.equal(root.get(Questao_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	public Questao findByEnunciado(String enunciado) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		query.where(builder.like(builder.lower(root.get(Questao_.enunciado)), "%" + enunciado.toLowerCase() + "%"));

		return this.manager.createQuery(query).getSingleResult();
	}

	public List<Questao> findByRefencia(String referencia) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		query.where(builder.like(builder.lower(root.get(Questao_.referencia)), "%" + referencia.toLowerCase() + "%"));
		return this.manager.createQuery(query).getResultList();
	}

	public List<Questao> findByCategoria(Categoria categoria) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		query.where(builder.equal(root.get(Questao_.subCategoria).get(SubCategoria_.categoria), categoria));
		return this.manager.createQuery(query).getResultList();
	}

	public List<Questao> findBySubCategoria(SubCategoria subCategoria) {
		CriteriaQuery<Questao> query = builder.createQuery(Questao.class);
		Root<Questao> root = query.from(Questao.class);
		query.where(builder.equal(root.get(Questao_.subCategoria), subCategoria));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Questao> root = criteria.from(Questao.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

}
