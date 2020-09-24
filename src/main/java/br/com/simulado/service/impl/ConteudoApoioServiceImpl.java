package br.com.simulado.service.impl;

import static java.lang.Long.parseLong;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.ConteudoApoioDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.ConteudoApoio;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.ConteudoApoioService;

public class ConteudoApoioServiceImpl implements ConteudoApoioService, Serializable {

	private static final long serialVersionUID = 1770466888112724361L;

	@Inject
	private ConteudoApoioDao repository;

	@Override
	public void save(ConteudoApoio conteudoApoio) throws Exception {
		if (conteudoApoio != null)
			repository.save(conteudoApoio);
		else
			throw new Exception("Object is null");
	}

	@Override
	public void update(ConteudoApoio conteudoApoio) throws Exception {
		if (conteudoApoio != null)
			repository.merge(conteudoApoio);
		else
			throw new Exception("Object is null");
	}

	@Override
	public List<ConteudoApoio> conteudoApoios() {
		return repository.todas() != null ? repository.todas() : Collections.emptyList();
	}

	@Override
	public List<ConteudoApoio> conteudoApoiosPorSubCategoria(SubCategoria subCategoria) throws Exception {
		if (subCategoria != null)
			return repository.findBySubCategoria(subCategoria);
		else
			throw new Exception("Object is null or Empty list");
	}

	@Override
	public ConteudoApoio findById(String id) throws Exception {
		return repository.findById(parseLong(id));
	}

	@Override
	public void mudarStatus(ConteudoApoio conteudoApoio) {
		repository.mudarStatus(conteudoApoio);		
	}

	@Override
	public List<ConteudoApoio> pesquisar(Filter filter) {
		return repository.pesquisar(filter);
	}

	@Override
	public List<ConteudoApoio> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

}
