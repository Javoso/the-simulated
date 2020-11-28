package br.com.simulado.email;

import java.io.File;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EnviadorDeEmail {

	private static final String REMETENTE = "The Simulated ";
	
	private ModuloEmail modulo;

	public EnviadorDeEmail(ConfiguracaoEmail configuracaoEmail) {
		modulo = new ModuloEmail(new HtmlEmail(), configuracaoEmail);
	}

	public void enviar(Email email, String parametro) throws EmailException{
		if (email.semDestinatario())
			throw new IllegalStateException("Selecione pelo menos um destinatário.");

		modulo.enviarEmail(email.getAssunto(), EmailTemplate.mensagem(email.getMensagem(), parametro), REMETENTE,
				email.getEmails(), true);
	}

	public void mensagem(String assunto, String parametro, String mensagem, File arquivo, List<String> destinatarios) throws EmailException{
		modulo.enviarEmail(assunto, EmailTemplate.mensagem(mensagem, parametro), arquivo, "The Simulated", destinatarios);
	}
}