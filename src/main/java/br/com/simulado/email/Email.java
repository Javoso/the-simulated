package br.com.simulado.email;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.simulado.models.Usuario;

public class Email {

	private String assunto;

	private String mensagem;

	private List<Usuario> destinatarios;

	public Email() {
		this.destinatarios = new ArrayList<>();
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Usuario> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<Usuario> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public void addDestinatario(Usuario destinatario) {
		if (destinatarios.contains(destinatario))
			return;

		destinatarios.add(destinatario);
	}

	public boolean semDestinatario() {
		return destinatarios.isEmpty();
	}

	public List<String> getEmails() {
		return destinatarios.stream().map(Usuario::getEmail).collect(Collectors.toList());
	}

}