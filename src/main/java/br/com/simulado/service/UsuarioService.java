package br.com.simulado.service;

import java.io.Serializable;

import org.apache.commons.mail.EmailException;

import br.com.simulado.models.Usuario;
import br.com.simulado.service.exception.NegocioException;

public interface UsuarioService extends Serializable {
	
	Usuario merge(Usuario usuario);

	void mudarStatus(Usuario usuario);
	
	void alteraSenha(Usuario usuario);
	
	Usuario findById(String id);
	
	Usuario findByEmail(String email);

	void persist(Usuario usuario) throws NegocioException, EmailException;

	Usuario login(String email);
	
}
