package br.com.simulado.service.impl;

import static br.com.simulado.util.DataUtil.getSaudacao;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;

import br.com.simulado.dao.UsuarioDao;
import br.com.simulado.email.Email;
import br.com.simulado.email.EnviadorDeEmail;
import br.com.simulado.infra.jpa.Transactional;
import br.com.simulado.models.Usuario;
import br.com.simulado.service.ConfiguracaoEmailService;
import br.com.simulado.service.UsuarioService;
import br.com.simulado.service.exception.NegocioException;

public class UsuarioServiceImpl implements UsuarioService {

	private static final long serialVersionUID = 1L;
	

	private static final String ASSUNTO = "E-mail de validação de dados do The Simulated";
	
	private static final String BR = "<br/>";
	
	@Inject
	private ConfiguracaoEmailService serviceEmail;

	private EnviadorDeEmail enviadorDeEmail;

	private Email email = new Email();

	@Inject
	private UsuarioDao usuarioDAO;

	@Override
	@Transactional
	public void merge(Usuario usuario) {
		usuarioDAO.merge(usuario);
	}

	@Override
	@Transactional
	public void persist(Usuario usuario) throws NegocioException, EmailException {
		usuario.resetarSenha();
		usuarioDAO.save(usuario);
		enviarEmail(usuario);
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
	public Usuario findByEmail(String email) {
		return usuarioDAO.findByEmail(email);
	}

	@Override
	public Usuario login(String email) {
		return usuarioDAO.login(email);
	}

	@Override
	public Usuario findById(String id) {
		return usuarioDAO.findById(Long.parseLong(id));
	}

	
	private void enviarEmail(Usuario usuario) throws EmailException {
		enviadorDeEmail = new EnviadorDeEmail(serviceEmail.getConfiguracao());
		email.addDestinatario(usuario);
		email.setAssunto(ASSUNTO);
		email.setMensagem(mensagemDeCadastro(usuario));
		enviadorDeEmail.enviar(email, ("?userid="+usuario.codificarId()));
		email = new Email();
	}
	
	private String mensagemDeCadastro(Usuario usuario) {
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Olá, "+ usuario.getNome().split(" ")[0]);
		mensagem.append(BR + getSaudacao() + BR);
		mensagem.append("Sua conta no <b>The</b> Simulated está quase pronta.").append
		("Para ativá-la, por favor confirme o seu endereço de email clicando no link abaixo." );
		return mensagem.toString();
	}

}