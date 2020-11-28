package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.SimuladoRespondidoDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.SimuladoRespondidoFilter;
import br.com.simulado.models.SimuladoRespondido;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.dto.SimuladoDTO;
import br.com.simulado.service.SimuladoRespondidoService;

public class SimuladoRespondidoServiceImpl implements SimuladoRespondidoService, Serializable {

	private static final long serialVersionUID = -4989390906062589280L;
	
	@Inject
	private SimuladoRespondidoDao repository;

	@Override
	public void save(SimuladoRespondido simuladoRespondido) throws Exception {
		if (nonNull(simuladoRespondido))
			repository.save(simuladoRespondido);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(SimuladoRespondido simuladoRespondido) throws Exception {
		if (nonNull(simuladoRespondido))
			repository.merge(simuladoRespondido);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<SimuladoRespondido> simuladosRespondidos() {
		return repository.todas();
	}

	@Override
	public List<SimuladoDTO> simuladosPorEstudante(Usuario estudante) {
		return repository.simuladosPorEstudante(estudante);
	}
	
	public SimuladoRespondido findById(String id) throws Exception {
		SimuladoRespondido simuladoRespondido = repository.findById(Long.parseLong(id));
		if (nonNull(simuladoRespondido))
			return simuladoRespondido;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(SimuladoRespondido simuladoRespondido) throws Exception {
		repository.mudarStatus(simuladoRespondido);
	}

	@Override
	public List<SimuladoRespondido> pesquisar(Filter filter) {
		SimuladoRespondidoFilter filtro = (SimuladoRespondidoFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<SimuladoRespondido> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public List<SimuladoRespondido> simuladoRespondidosByEstudante(Usuario estudante) {
		return repository.simuladoRespondidoPorEstudante(estudante);
	}
}
