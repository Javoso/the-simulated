package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.SubCategoria;

public interface SubCategoriaService {
	
	public void save(SubCategoria subCategoria) throws Exception;
	
	public void update(SubCategoria subCategoria) throws Exception;
	
	public List<SubCategoria> subCategoriasPorCategoria(Categoria categoria) throws Exception;

	public List<SubCategoria> subCategorias();
	
	public SubCategoria findById(String id) throws Exception;

	public void mudarStatus(SubCategoria subCategoria);
	
	public List<SubCategoria> pesquisar(Filter filter);

	public List<SubCategoria> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);
}
