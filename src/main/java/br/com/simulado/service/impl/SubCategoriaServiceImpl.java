package br.com.simulado.service.impl;

import static java.lang.Long.parseLong;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.SubCategoriaDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.SubCategoriaService;

public class SubCategoriaServiceImpl implements SubCategoriaService, Serializable {

	private static final long serialVersionUID = 1770466888112724361L;

	@Inject
	private SubCategoriaDao repository;

	@Override
	public void save(SubCategoria subCategoria) throws Exception {
		if (subCategoria != null)
			repository.save(subCategoria);
		else
			throw new Exception("Object is null");
	}

	@Override
	public void update(SubCategoria subCategoria) throws Exception {
		if (subCategoria != null)
			repository.merge(subCategoria);
		else
			throw new Exception("Object is null");
	}

	@Override
	public List<SubCategoria> subCategorias() {
		return repository.todas() != null ? repository.todas() : Collections.emptyList();
	}

	@Override
	public List<SubCategoria> subCategoriasPorCategoria(Categoria categoria) throws Exception {
		if (categoria != null)
			return repository.findByCategoria(categoria);
		else
			throw new Exception("Object is null or Empty list");
	}

	@Override
	public SubCategoria findById(String id) throws Exception {
		return repository.findById(parseLong(id));
	}

	@Override
	public void mudarStatus(SubCategoria subCategoria) {
		repository.mudarStatus(subCategoria);		
	}

	@Override
	public List<SubCategoria> pesquisar(Filter filter) {
		return repository.pesquisar(filter);
	}

	@Override
	public List<SubCategoria> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

}
