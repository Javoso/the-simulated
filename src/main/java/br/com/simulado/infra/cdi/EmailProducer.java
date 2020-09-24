package br.com.simulado.infra.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.apache.commons.mail.HtmlEmail;

public class EmailProducer {
	
	@Produces
	@RequestScoped
	public HtmlEmail getHtmlEmail() {
		return new HtmlEmail();
	}
}