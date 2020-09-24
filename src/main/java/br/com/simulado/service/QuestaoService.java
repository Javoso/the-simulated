package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Questao;
import br.com.simulado.models.SubCategoria;

public interface QuestaoService {
	
	public void save(Questao questao) throws Exception ;
	
	public void update(Questao questao) throws Exception;
	
	public Questao findById(String id) throws Exception;
	
	public Questao findByEnunciado(String enunciado) throws Exception;
	
	public List<Questao> findByReferencia(String referencia) throws Exception;

	public List<Questao> findByCategoria(Categoria categoria) throws Exception;

	public List<Questao> findBySubCategoria(SubCategoria subCategoria) throws Exception;
	
	public void mudarStatus(Questao questao) throws Exception;
	
	public List<Questao> questoes();
		
	public List<Questao> pesquisar(Filter filter);

	public List<Questao> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
