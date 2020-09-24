package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.CategoriaDao;
import br.com.simulado.dao.filter.CategoriaFilter;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.constantes.TipoCategoria;
import br.com.simulado.service.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService, Serializable {

	private static final long serialVersionUID = 5226056167002436012L;

	@Inject
	private CategoriaDao repository;

	@Override
	public void save(Categoria categoria) throws Exception {
		if (nonNull(categoria))
			repository.save(categoria);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(Categoria categoria) throws Exception {
		if (nonNull(categoria))
			repository.merge(categoria);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<Categoria> categorias() {
		return repository.todas();
	}

	public Categoria findById(String id) throws Exception {
		Categoria categoria = repository.findById(Long.parseLong(id));
		if (nonNull(categoria))
			return categoria;
		else
			throw new Exception("Object not found");
	}
	
	public Categoria findByNome(String nome) throws Exception {
		Categoria categoria = repository.findByNome(nome);
		if (nonNull(categoria))
			return categoria;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(Categoria categoria) throws Exception {
		repository.mudarStatus(categoria);
	}

	@Override
	public List<Categoria> pesquisar(Filter filter) {
		CategoriaFilter filtro = (CategoriaFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<Categoria> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public List<Categoria> categoriasByTipo(TipoCategoria tipoCategoria) {
		return repository.categoriasByTipoCategoria(tipoCategoria);
	}

}
