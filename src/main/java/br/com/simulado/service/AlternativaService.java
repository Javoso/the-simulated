package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Questao;

public interface AlternativaService {
	
	public void save(Alternativa alternativa) throws Exception ;
	
	public void update(Alternativa alternativa) throws Exception;
	
	public Alternativa findById(String id) throws Exception;
	
	public Alternativa findByEnunciado(String enunciado) throws Exception;

	public List<Alternativa> findByQuestao(Questao questao) throws Exception;

	public void mudarStatus(Alternativa alternativa) throws Exception;
	
	public List<Alternativa> alternativas();
		
	public List<Alternativa> pesquisar(Filter filter);

	public List<Alternativa> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
