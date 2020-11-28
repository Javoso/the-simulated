package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.SimuladoRespondido;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.dto.SimuladoDTO;

public interface SimuladoRespondidoService {
	
	public void save(SimuladoRespondido simulado) throws Exception ;
	
	public void update(SimuladoRespondido simulado) throws Exception;
	
	public SimuladoRespondido findById(String id) throws Exception;
	
	public void mudarStatus(SimuladoRespondido simulado) throws Exception;
	
	public List<SimuladoRespondido> simuladosRespondidos();
	
	public List<SimuladoRespondido> simuladoRespondidosByEstudante(Usuario estudante);
	
	public List<SimuladoDTO> simuladosPorEstudante(Usuario estudante);
	
	public List<SimuladoRespondido> pesquisar(Filter filter);

	public List<SimuladoRespondido> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
