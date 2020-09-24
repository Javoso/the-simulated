package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.ConteudoApoio;
import br.com.simulado.models.SubCategoria;

public interface ConteudoApoioService {
	
	public void save(ConteudoApoio conteudoApoio) throws Exception;
	
	public void update(ConteudoApoio conteudoApoio) throws Exception;
	
	public List<ConteudoApoio> conteudoApoiosPorSubCategoria(SubCategoria subCategoria) throws Exception;

	public List<ConteudoApoio> conteudoApoios();
	
	public ConteudoApoio findById(String id) throws Exception;

	public void mudarStatus(ConteudoApoio conteudoApoio);
	
	public List<ConteudoApoio> pesquisar(Filter filter);

	public List<ConteudoApoio> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);
}
