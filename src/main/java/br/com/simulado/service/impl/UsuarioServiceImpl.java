package br.com.simulado.service.impl;

import javax.inject.Inject;

import br.com.simulado.dao.UsuarioDao;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Usuario;
import br.com.simulado.service.UsuarioService;
import br.com.simulado.service.exception.NegocioException;

public class UsuarioServiceImpl implements UsuarioService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarioDAO;

	@Override
	@Transactional
	public void merge(Usuario usuario) {
		usuarioDAO.merge(usuario);
	}

	@Override
	@Transactional
	public void persist(Usuario usuario) throws NegocioException {
		usuario.resetarSenha();
		usuarioDAO.save(usuario);
	}

	@Override
	@Transactional
	public void mudarStatus(Usuario usuario) {
		usuarioDAO.mudarStatus(usuario);
	}

	@Override
	@Transactional
	public void alteraSenha(Usuario usuario) {
		usuarioDAO.updatePassword(usuario);
	}

	@Override
	public Usuario findByMatricula(String matricula) {
		return usuarioDAO.findByEmail(matricula);
	}

	@Override
	public Usuario login(String email) {
		return usuarioDAO.login(email);
	}
}