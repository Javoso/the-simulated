package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.TentativaDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.TentativaFilter;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;
import br.com.simulado.service.TentativaService;

public class TentativaServiceImpl implements TentativaService, Serializable {

	private static final long serialVersionUID = 7941860686687673915L;
	
	@Inject
	private TentativaDao repository;

	@Override
	public void save(Tentativa tentativa) throws Exception {
		if (nonNull(tentativa))
			repository.save(tentativa);
		else
			throw new Exception("Objects is null");
	}
	
	public Tentativa salvar(Tentativa tentativa) throws Exception {
		if (nonNull(tentativa))
			return repository.merge(tentativa);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(Tentativa tentativa) throws Exception {
		if (nonNull(tentativa))
			repository.merge(tentativa);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<Tentativa> tentativas() {
		return repository.todas();
	}

	public Tentativa findById(String id) throws Exception {
		Tentativa tentativa = repository.findById(Long.parseLong(id));
		if (nonNull(tentativa))
			return tentativa;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(Tentativa tentativa) throws Exception {
		repository.mudarStatus(tentativa);
	}

	@Override
	public List<Tentativa> pesquisar(Filter filter) {
		TentativaFilter filtro = (TentativaFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<Tentativa> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public Tentativa  findByEstudante(Usuario estudante) throws Exception {
		Tentativa tentativa = repository.findByEstudante(estudante);
		if (nonNull(tentativa))
			return tentativa;
		else
			throw new Exception("Object not found");
	}

	@Override
	public List<Tentativa> findBySimulado(Simulado simulado) throws Exception {
		List<Tentativa> tentativas = repository.findBySimulado(simulado);
		if (nonNull(tentativas))
			return tentativas;
		else
			throw new Exception("Objects not found");
	}

	@Override
	public List<Tentativa> findBySimulado(Long id) throws Exception {
		return repository.findBySimulado(id);
	}


}
