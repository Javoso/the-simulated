package br.com.simulado.security.constant;

import br.com.simulado.infra.cdi.CDIServiceLocator;
import br.com.simulado.infra.jsf.ProjectFaces;

public enum Navegacao {

	// @formatter:off
	LOGOUT("/logout"),
	LOGIN("/login.xhtml");

	private String url;

	private Navegacao(String url) {
		this.url = url;
	}

	public String url() {
		return url;
	}

	public static void redirectTo(Navegacao navegacao) {
		CDIServiceLocator.getBean(ProjectFaces.class).redirect(navegacao.url);
	}
}
