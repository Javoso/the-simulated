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
import br.com.simulado.dao.filter.SubCategoriaFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Categoria_;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.models.SubCategoria_;

public class SubCategoriaDao implements DAO<SubCategoria>, Serializable {

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
	public SubCategoria merge(SubCategoria subCategoria) {
		return this.manager.merge(subCategoria);
	}

	@Override
	@Transactional
	public void save(SubCategoria subCategoria) {
		this.manager.persist(subCategoria);
	}

	@Override
	public List<SubCategoria> todas() {
		CriteriaQuery<SubCategoria> query = builder.createQuery(SubCategoria.class);
		Root<SubCategoria> root = query.from(SubCategoria.class);
		query.orderBy(builder.asc(root.get(SubCategoria_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<SubCategoria> pesquisar(Filter filtro) {
		CriteriaQuery<SubCategoria> query = builder.createQuery(SubCategoria.class);
		Root<SubCategoria> root = query.from(SubCategoria.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(SubCategoria_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<SubCategoria> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<SubCategoria> criteria = builder.createQuery(SubCategoria.class);
		Root<SubCategoria> root = criteria.from(SubCategoria.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(SubCategoria_.nome)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(SubCategoria subCategoria) {
		CriteriaUpdate<SubCategoria> criteria = builder.createCriteriaUpdate(SubCategoria.class);
		Root<SubCategoria> root = criteria.from(SubCategoria.class);
		criteria.set(root.get(SubCategoria_.status), !subCategoria.isAtivo()).where(builder.equal(root, subCategoria));
		this.manager.createQuery(criteria).executeUpdate();
	}
	
	@Override
	public SubCategoria findById(Long id) {
		CriteriaQuery<SubCategoria> query = builder.createQuery(SubCategoria.class);
		Root<SubCategoria> root = query.from(SubCategoria.class);
		query.where(builder.equal(root.get(SubCategoria_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<SubCategoria> root) {
		
		SubCategoriaFilter filtro = (SubCategoriaFilter) filter;

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temTitulo())
			predicates.add(
					builder.like(builder.lower(root.get(SubCategoria_.nome)), "%" + filtro.getNome().toLowerCase() + "%"));

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(SubCategoria_.status), filtro.isStatus()));
		
		if (filtro.temTipo())
			predicates.add(builder.equal(root.get(SubCategoria_.categoria).get(Categoria_.tipoCategoria), filtro.getTipoCategoria()));
			
		if (filtro.temCategoria())
			predicates.add(builder.equal(root.get(SubCategoria_.categoria), filtro.getCategoria()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public List<SubCategoria> findByCategoria(Categoria categoria) {
		CriteriaQuery<SubCategoria> criteriaQuery = builder.createQuery(SubCategoria.class);
		Root<SubCategoria> root = criteriaQuery.from(SubCategoria.class);
		criteriaQuery.where(builder.equal(root.get(SubCategoria_.categoria), categoria));
		criteriaQuery.orderBy(builder.asc(root.get(SubCategoria_.nome)));
		return manager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<SubCategoria> root = criteria.from(SubCategoria.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

}
