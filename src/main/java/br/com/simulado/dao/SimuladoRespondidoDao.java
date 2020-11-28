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
import br.com.simulado.dao.filter.SimuladoRespondidoFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.SimuladoRespondido;
import br.com.simulado.models.SimuladoRespondido_;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.dto.SimuladoDTO;

public class SimuladoRespondidoDao implements DAO<SimuladoRespondido>, Serializable {

	private static final long serialVersionUID = -2475720124949878757L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		this.builder = this.manager.getCriteriaBuilder();
	}

	@Override
	@Transactional
	public SimuladoRespondido merge(SimuladoRespondido simulado) {
		return this.manager.merge(simulado);
	}

	@Override
	@Transactional
	public void save(SimuladoRespondido simulado) {
		this.manager.persist(simulado);
	}

	public List<SimuladoDTO> simuladosPorEstudante(Usuario estudante) {
		CriteriaQuery<SimuladoDTO> criteria = builder.createQuery(SimuladoDTO.class);
		Root<SimuladoRespondido> proc = criteria.from(SimuladoRespondido.class);
		criteria.select(builder.construct(SimuladoDTO.class, proc))
				.where(builder.equal(proc.get(SimuladoRespondido_.estudante), estudante))
				.orderBy(builder.asc(proc.get(SimuladoRespondido_.id)));
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<SimuladoRespondido> todas() {
		CriteriaQuery<SimuladoRespondido> query = builder.createQuery(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = query.from(SimuladoRespondido.class);
		query.orderBy(builder.asc(root.get(SimuladoRespondido_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<SimuladoRespondido> pesquisar(Filter filtro) {
		CriteriaQuery<SimuladoRespondido> query = builder.createQuery(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = query.from(SimuladoRespondido.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(SimuladoRespondido_.id)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<SimuladoRespondido> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<SimuladoRespondido> criteria = builder.createQuery(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = criteria.from(SimuladoRespondido.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(SimuladoRespondido_.id)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(SimuladoRespondido simuladoRespondido) {
		CriteriaUpdate<SimuladoRespondido> criteria = builder.createCriteriaUpdate(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = criteria.from(SimuladoRespondido.class);
		criteria.set(root.get(SimuladoRespondido_.status), !simuladoRespondido.isAtivo())
				.where(builder.equal(root, simuladoRespondido));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<SimuladoRespondido> root) {
		SimuladoRespondidoFilter filtro = (SimuladoRespondidoFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(SimuladoRespondido_.status), filtro.isStatus()));

		if (filtro.temEstudante())
			predicates.add(builder.equal(root.get(SimuladoRespondido_.estudante), filtro.getEstudante()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public SimuladoRespondido findById(Long id) {
		CriteriaQuery<SimuladoRespondido> query = builder.createQuery(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = query.from(SimuladoRespondido.class);
		query.where(builder.equal(root.get(SimuladoRespondido_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<SimuladoRespondido> root = criteria.from(SimuladoRespondido.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

	public List<SimuladoRespondido> simuladoRespondidoPorEstudante(Usuario estudante) {
		CriteriaQuery<SimuladoRespondido> criteria = builder.createQuery(SimuladoRespondido.class);
		Root<SimuladoRespondido> root = criteria.from(SimuladoRespondido.class);
		criteria.where(builder.equal(root.get(SimuladoRespondido_.estudante), estudante));
		return manager.createQuery(criteria).getResultList();
	}

}
