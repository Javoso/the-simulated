package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.constantes.TipoSimulado;

public interface SimuladoService {
	
	public void save(Simulado simulado) throws Exception ;
	
	public void update(Simulado simulado) throws Exception;
	
	public Simulado findById(String id) throws Exception;
	
	public Simulado findByNome(String nome) throws Exception;

	public void mudarStatus(Simulado simulado) throws Exception;
	
	public List<Simulado> simulados();
	
	public List<Simulado> simuladosByTipo(TipoSimulado simulado);
	
	public List<Simulado> pesquisar(Filter filter);

	public List<Simulado> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
