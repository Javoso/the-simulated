package br.com.simulado.dao;

import static javax.persistence.criteria.JoinType.LEFT;

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
import br.com.simulado.dao.filter.UsuarioFilter;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.Usuario_;

public class UsuarioDao implements DAO<Usuario>, Serializable {

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
	public Usuario merge(Usuario usuario) {
		return this.manager.merge(usuario);
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		this.manager.persist(usuario);
	}

	@Override
	public List<Usuario> todas() {
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		query.orderBy(builder.asc(root.get(Usuario_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Usuario> pesquisar(Filter filtro) {
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		Predicate[] condicao = restricoes(filtro, root);
		query.where(condicao);
		query.orderBy(builder.asc(root.get(Usuario_.nome)));
		return this.manager.createQuery(query).getResultList();
	}

	@Override
	public List<Usuario> pesquisar(int primeiro, int ultimo, Filter filtro) {
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		Predicate[] condicao = restricoes(filtro, root);
		criteria.where(condicao);
		criteria.orderBy(builder.asc(root.get(Usuario_.nome)));
		return this.manager.createQuery(criteria).setFirstResult(primeiro).setMaxResults(ultimo).getResultList();
	}

	@Override
	@Transactional
	public void mudarStatus(Usuario usuario) {
		CriteriaUpdate<Usuario> criteria = builder.createCriteriaUpdate(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		criteria.set(root.get(Usuario_.status), !usuario.isAtivo()).where(builder.equal(root, usuario));
		this.manager.createQuery(criteria).executeUpdate();
	}

	@Override
	public Predicate[] restricoes(Filter filter, Root<Usuario> root) {
		UsuarioFilter filtro = (UsuarioFilter) filter;
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.temTitulo())
			predicates.add(
					builder.like(builder.lower(root.get(Usuario_.nome)), "%" + filtro.getNome().toLowerCase() + "%"));

		if (filtro.temStatus())
			predicates.add(builder.equal(root.get(Usuario_.status), filtro.isStatus()));

		if (filtro.temPermissao())
			predicates.add(builder.equal(root.get(Usuario_.permissoes), filtro.isStatus()));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public Usuario findById(Long id) {
		CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		query.where(builder.equal(root.get(Usuario_.id), id));
		return this.manager.createQuery(query).getSingleResult();
	}

	@Override
	public Long count(Filter filter) {
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		criteria.select(builder.count(root));
		Predicate[] predicates = restricoes(filter, root);
		criteria.where(predicates);
		return manager.createQuery(criteria).getSingleResult();
	}

	public Usuario findByEmail(String email) {
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		criteria.where(builder.equal(root.get(Usuario_.email), email))
				.where(builder.equal(root.get(Usuario_.status), true));
		return manager.createQuery(criteria).getSingleResult();
	}

	public void updatePassword(Usuario usuario) {
		CriteriaUpdate<Usuario> criteria = builder.createCriteriaUpdate(Usuario.class);
		Root<Usuario> u = criteria.from(Usuario.class);
		criteria.set(u.get(Usuario_.senha), usuario.getSenha()).set(u.get(Usuario_.mudarSenha), usuario.isMudarSenha())

				.where(builder.equal(u, usuario));

		manager.createQuery(criteria).executeUpdate();
	}

	public Usuario login(String email) {
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> u = criteria.from(Usuario.class);
		u.fetch(Usuario_.permissoes, LEFT);
		Predicate[] ref = loginRestricoes(email, u);
		criteria.select(u).where(ref);

		return manager.createQuery(criteria).getSingleResult();
	}

	public Predicate[] loginRestricoes(String email, Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.like(builder.lower(root.get(Usuario_.email)), "%" + email + "%"));

		predicates.add(builder.equal(root.get(Usuario_.status), true));

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
