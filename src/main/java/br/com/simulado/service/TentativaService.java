package br.com.simulado.service;

import java.util.List;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;

public interface TentativaService {
	
	public void save(Tentativa tentativa) throws Exception ;
	
	public Tentativa salvar(Tentativa tentativa) throws Exception ;
	
	public void update(Tentativa tentativa) throws Exception;
	
	public Tentativa findById(String id) throws Exception;
	
	public Tentativa findByEstudante(Usuario estudante) throws Exception;

	public List<Tentativa> findBySimulado(Simulado simulado) throws Exception;
	
	public List<Tentativa> findBySimulado(Long id) throws Exception;

	public void mudarStatus(Tentativa tentativa) throws Exception;
	
	public List<Tentativa> tentativas();
		
	public List<Tentativa> pesquisar(Filter filter);

	public List<Tentativa> pesquisar(int first, int pageSize, Filter filter);

	public Long count(Filter filter);

}
