package br.com.simulado.service;

import java.io.Serializable;

import br.com.simulado.models.Usuario;
import br.com.simulado.service.exception.NegocioException;
import br.com.simulado.service.exception.RegistroExistenteException;

public interface UsuarioService extends Serializable {
	
	/**
	 * 
	 * @param usuario
	 * @throws RegistroExistenteException 
	 */
	void merge(Usuario usuario);

	/**
	 * 
	 * @param usuario
	 */
	void mudarStatus(Usuario usuario);
	
	/**
	 * 
	 * @param usuario
	 */
	void alteraSenha(Usuario usuario);
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	Usuario findByMatricula(String matricula);

	/**
	 * 
	 * @param usuario
	 * @throws NegocioException 
	 */
	void persist(Usuario usuario) throws NegocioException;

	/**
	 * 
	 * @param matricula
	 * @return
	 */
	Usuario login(String matricula);

}
