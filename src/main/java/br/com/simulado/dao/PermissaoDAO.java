package br.com.simulado.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Permissao;
import br.com.simulado.models.Permissao_;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.Usuario_;

public class PermissaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient EntityManager manager;

	private transient CriteriaBuilder builder;

	@PostConstruct
	public void init() {
		this.builder = this.manager.getCriteriaBuilder();
	}

	@Transactional
	public Permissao save(Permissao permissao) {
		return manager.merge(permissao);
	}

	public Permissao findByNome(String nome) {
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		Root<Permissao> root = criteria.from(Permissao.class);
		criteria.where(builder.like(builder.lower(root.get(Permissao_.nome)), "%" + nome.toLowerCase() + "%"));
		return manager.createQuery(criteria).getSingleResult();
	}

	public List<Permissao> findAll() {
		CriteriaQuery<Permissao> query = builder.createQuery(Permissao.class);
		Root<Permissao> root = query.from(Permissao.class);
		query.orderBy(builder.asc(root.get(Permissao_.nome)));
		return this.manager.createQuery(query).getResultList();

	}

	public List<String> nomesPermissoesPorUsuario(Usuario grupo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<String> criteria = builder.createQuery(String.class);
		Root<Usuario> g = criteria.from(Usuario.class);
		Join<Usuario, Permissao> p = g.join(Usuario_.permissoes);
		criteria.select(p.get(Permissao_.nome)).where(builder.equal(g, grupo))
				.orderBy(builder.asc(p.get(Permissao_.nome)));
		return manager.createQuery(criteria).getResultList();
	}

	public List<Permissao> porUsuario(Integer id) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		Root<Usuario> g = criteria.from(Usuario.class);
		Join<Usuario, Permissao> p = g.join(Usuario_.permissoes);
		criteria.select(p).where(builder.equal(g.get(Usuario_.id), id)).orderBy(builder.asc(p.get(Permissao_.nome)));
		return manager.createQuery(criteria).getResultList();
	}

}