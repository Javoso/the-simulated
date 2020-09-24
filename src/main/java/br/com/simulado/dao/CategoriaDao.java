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

import br.com.simulado.dao.filter.CategoriaFilter;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Categoria_;
import br.com.simulado.models.constantes.TipoCategoria;

public class CategoriaDao implements DAO<Categoria>, Serializable {

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
	public Categoria merge(Categoria categoria) {
		return this.manager.merge(categoria);
	}

	@Override
	@Transactional
	public void save(Categoria categoria) {
		this.manager.persist(categoria);
	}

	@Override
	public List<Categoria> todas() {
		CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);
		query.orderBy(builder.asc(root.get(Categoria_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Categoria> pesquisar(Filter filtro) {
		CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Categoria_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Categoria> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Categoria_.nome)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Categoria categoria) {
		CriteriaUpdate<Categoria> criteria = builder.createCriteriaUpdate(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		criteria.set(root.get(Categoria_.status), !categoria.isAtivo()).where(builder.equal(root, categoria));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Categoria> root) {
		CategoriaFilter filtro = (CategoriaFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temTitulo())
			predicates.add(
					builder.like(builder.lower(root.get(Categoria_.nome)), "%" + filtro.getNome().toLowerCase() + "%"));

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Categoria_.status), filtro.isStatus()));

		if (filtro.temTipo())
			predicates.add(builder.equal(root.get(Categoria_.tipoCategoria), filtro.getTipoCategoria()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Categoria findById(Long id) {
		CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);
		query.where(builder.equal(root.get(Categoria_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

	public List<Categoria> categoriasByTipoCategoria(TipoCategoria tipoCategoria) {
		CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		criteria.where(builder.equal(root.get(Categoria_.tipoCategoria), tipoCategoria));
		return manager.createQuery(criteria).getResultList();
	}

	public Categoria findByNome(String nome) {
		CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		criteria.where(builder.equal(root.get(Categoria_.nome), nome));
		return manager.createQuery(criteria).getSingleResult();
	}

}
