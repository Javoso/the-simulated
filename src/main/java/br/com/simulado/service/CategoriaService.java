package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.constantes.TipoCategoria;

public interface CategoriaService {
	
	public void save(Categoria categoria) throws Exception ;
	
	public void update(Categoria categoria) throws Exception;
	
	public Categoria findById(String id) throws Exception;
	
	public Categoria findByNome(String nome) throws Exception;

	public void mudarStatus(Categoria categoria) throws Exception;
	
	public List<Categoria> categorias();
	
	public List<Categoria> categoriasByTipo(TipoCategoria categoria);
	
	public List<Categoria> pesquisar(Filter filter);

	public List<Categoria> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
