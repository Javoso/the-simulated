package br.com.simulado.service.impl;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.simulado.dao.QuestaoDao;
import br.com.simulado.dao.filter.Filter;
import br.com.simulado.dao.filter.QuestaoFilter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Questao;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.QuestaoService;

public class QuestaoServiceImpl implements QuestaoService, Serializable {

	private static final long serialVersionUID = 5226056167002436012L;

	@Inject
	private QuestaoDao repository;

	@Override
	public void save(Questao questao) throws Exception {
		if (nonNull(questao))
			repository.save(questao);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public void update(Questao questao) throws Exception {
		if (nonNull(questao))
			repository.merge(questao);
		else
			throw new Exception("Objects is null");
	}

	@Override
	public List<Questao> questoes() {
		return repository.todas();
	}

	public Questao findById(String id) throws Exception {
		Questao questao = repository.findById(Long.parseLong(id));
		if (nonNull(questao))
			return questao;
		else
			throw new Exception("Object not found");
	}

	@Override
	public void mudarStatus(Questao questao) throws Exception {
		repository.mudarStatus(questao);
	}

	@Override
	public List<Questao> pesquisar(Filter filter) {
		QuestaoFilter filtro = (QuestaoFilter) filter;
		return repository.pesquisar(filtro);
	}

	@Override
	public List<Questao> pesquisar(int first, int pageSize, Filter filter) {
		return repository.pesquisar(first, pageSize, filter);
	}

	@Override
	public Long count(Filter filter) {
		return repository.count(filter);
	}

	@Override
	public Questao findByEnunciado(String enunciado) throws Exception {
		Questao questao = repository.findByEnunciado(enunciado);
		if (nonNull(questao))
			return questao;
		else
			throw new Exception("Object not found");
	}

	@Override
	public List<Questao> findByReferencia(String referencia) throws Exception {
		List<Questao> questao = repository.findByRefencia(referencia);
		if (nonNull(questao))
			return questao;
		else
			throw new Exception("Objects not found");
	}

	@Override
	public List<Questao> findByCategoria(Categoria categoria) throws Exception {
		List<Questao> questoes = repository.findByCategoria(categoria);
		if (nonNull(questoes))
			return questoes;
		else
			throw new Exception("Objects not found");
	}

	@Override
	public List<Questao> findBySubCategoria(SubCategoria subCategoria) throws Exception {
		List<Questao> questoes = repository.findBySubCategoria(subCategoria);
		if (nonNull(questoes))
			return questoes;
		else
			throw new Exception("Objects not found");
	}

}
