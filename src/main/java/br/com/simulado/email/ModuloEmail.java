package br.com.simulado.email;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.com.simulado.util.AES;

public class ModuloEmail {

	private HtmlEmail htmlEmail;
	
	private ConfiguracaoEmail configuracao;

	public ModuloEmail(HtmlEmail htmlEmail, ConfiguracaoEmail configuracaoEmail) {
		this.htmlEmail = htmlEmail;
		this.configuracao = configuracaoEmail;
	}

	private void enviarEmail(String assunto, String mensagem, String remetente, boolean temAnexo, File anexo,
			List<String> destinatarios, boolean bcc) throws EmailException {

		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(() -> {
			
				try {
					configurar(assunto, remetente, mensagem);
				for (String destinatario : destinatarios)
					enviarPara(destinatario, bcc);

				if (temAnexo)
					htmlEmail.attach(anexo);

				htmlEmail.send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
			
		});

		service.shutdown();
		service = null;
	}

	public void enviarEmail(String assunto, String mensagem, String remetente, String destinatario) throws EmailException{
		this.enviarEmail(assunto, mensagem, remetente, false, null, Arrays.asList(destinatario), false) ;
	}

	public void enviarEmail(String assunto, String mensagem, String remetente, List<String> destinatarios) throws EmailException{
		this.enviarEmail(assunto, mensagem, remetente, false, null, destinatarios, false);
	}

	public void enviarEmail(String assunto, String mensagem, String remetente, List<String> destinatarios,
			boolean bcc) throws EmailException{
		this.enviarEmail(assunto, mensagem, remetente, false, null, destinatarios, bcc);
	}

	public void enviarEmail(String assunto, String mensagem, File arquivo, String remetente,
			List<String> destinatario) throws EmailException{
		this.enviarEmail(assunto, mensagem, remetente, true, arquivo, destinatario, false);
	}

	private void configurar(String assunto, String remetente, String mensagem) throws EmailException {
		htmlEmail.setHostName(configuracao.getHostname());
		htmlEmail.setSmtpPort(configuracao.getPort());
		htmlEmail.setStartTLSEnabled(true);
		htmlEmail.setFrom(configuracao.getUsername(), remetente);
		htmlEmail.setSubject(assunto);
		htmlEmail.setHtmlMsg(mensagem);
		htmlEmail.setSSLOnConnect(configuracao.getSsl());
		htmlEmail.setAuthentication(configuracao.getUsername(), getSenha());
	}

	private String getSenha() {
		return new AES().decodificar(configuracao.getPassword());
	}

	private void enviarPara(String destinatario, boolean bcc) {
		try {
			if (bcc)
				htmlEmail.addBcc(destinatario);
			else
				htmlEmail.addTo(destinatario);
		} catch (EmailException e) {
			throw new RuntimeException(e);
		}
	}
}