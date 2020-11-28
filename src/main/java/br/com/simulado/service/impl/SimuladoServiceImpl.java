package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.SimuladoDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.SimuladoFilter;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.constantes.TipoSimulado;
import br.com.simulado.service.SimuladoService;

public class SimuladoServiceImpl implements SimuladoService, Serializable {

	private static final long serialVersionUID = 2878887705430828629L;

	@Inject
	private SimuladoDao repository;

	@Override
	public void save(Simulado simulado) throws Exception {
		if (nonNull(simulado))
			repository.save(simulado);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(Simulado simulado) throws Exception {
		if (nonNull(simulado))
			repository.merge(simulado);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<Simulado> simulados() {
		return repository.todas();
	}

	public Simulado findById(String id) throws Exception {
		Simulado simulado = repository.findById(Long.parseLong(id));
		if (nonNull(simulado))
			return simulado;
		else
			throw new Exception("Object not found");
	}
	
	public Simulado findByNome(String nome) throws Exception {
		Simulado simulado = repository.findByNome(nome);
		if (nonNull(simulado))
			return simulado;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(Simulado simulado) throws Exception {
		repository.mudarStatus(simulado);
	}

	@Override
	public List<Simulado> pesquisar(Filter filter) {
		SimuladoFilter filtro = (SimuladoFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<Simulado> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public List<Simulado> simuladosByTipo(TipoSimulado tipoSimulado) {
		return repository.simuladosByTipoSimulado(tipoSimulado);
	}

}
