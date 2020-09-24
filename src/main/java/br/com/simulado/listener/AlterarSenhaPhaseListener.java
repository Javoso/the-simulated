package br.com.simulado.listener;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.core.context.SecurityContextImpl;

import br.com.simulado.infra.jsf.FacesUtil;
import br.com.simulado.models.Usuario;
import br.com.simulado.security.UsuarioSistema;

public class AlterarSenhaPhaseListener implements PhaseListener {

	private static final String URL_ALTERAR_SENHA_REDIRECT = "/usuario/alterar-senha.xhtml?faces-redirect=true";

	private static final String URL_ALTERAR_SENHA = "/usuario/alterar-senha.xhtml";

	private static final String URL_LOGIN = "/login.xhtml";

	private static final long serialVersionUID = 469449261640291781L;

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		try {
			if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
				String url = FacesUtil.getUrl();
			if (!url.equals(URL_ALTERAR_SENHA) && !url.equals(URL_LOGIN)) {
					Usuario usuarioLogado = getUsuarioLogado();
					
					if (usuarioLogado == null)
						return;

					if (usuarioLogado.isMudarSenha()) {
						navigateToView(FacesContext.getCurrentInstance(), URL_ALTERAR_SENHA_REDIRECT);
					}
				}
			}
		} catch (NullPointerException e) {
			System.err.println("Erro ao barrar usuário!");
		}

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	private void navigateToView(FacesContext context, String view) {
		context.getApplication().getNavigationHandler().handleNavigation(context, null, view);
	}

	private Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		SecurityContextImpl sci = (SecurityContextImpl) external.getSessionMap().get("SPRING_SECURITY_CONTEXT");
		
		if (sci == null)
			return null;

		UsuarioSistema user = (UsuarioSistema) sci.getAuthentication().getPrincipal();
		return user.getUsuario();
	}
}