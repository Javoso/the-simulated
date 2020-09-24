package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.AlternativaDao;
import br.com.simulado.dao.filter.AlternativaFilter;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Questao;
import br.com.simulado.service.AlternativaService;

public class AlternativaServiceImpl implements AlternativaService, Serializable {

	private static final long serialVersionUID = 5226056167002436012L;

	@Inject
	private AlternativaDao repository;

	@Override
	public void save(Alternativa alternativa) throws Exception {
		if (nonNull(alternativa))
			repository.save(alternativa);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(Alternativa alternativa) throws Exception {
		if (nonNull(alternativa))
			repository.merge(alternativa);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<Alternativa> alternativas() {
		return repository.todas();
	}

	public Alternativa findById(String id) throws Exception {
		Alternativa alternativa = repository.findById(Long.parseLong(id));
		if (nonNull(alternativa))
			return alternativa;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(Alternativa alternativa) throws Exception {
		repository.mudarStatus(alternativa);
	}

	@Override
	public List<Alternativa> pesquisar(Filter filter) {
		AlternativaFilter filtro = (AlternativaFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<Alternativa> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public Alternativa findByEnunciado(String enunciado) throws Exception {
		Alternativa alternativa = repository.findByEnunciado(enunciado);
		if (nonNull(alternativa))
			return alternativa;
		else
			throw new Exception("Object not found");
	}

	@Override
	public List<Alternativa> findByQuestao(Questao questao) throws Exception {
		List<Alternativa> alternativas = repository.findByQuestao(questao);
		if (nonNull(alternativas))
			return alternativas;
		else
			throw new Exception("Objects not found");
	}

}
