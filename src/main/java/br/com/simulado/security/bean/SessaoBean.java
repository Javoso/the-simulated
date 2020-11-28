package br.com.simulado.security.bean;

import static br.com.simulado.security.constant.Navegacao.LOGIN;
import static br.com.simulado.security.constant.Navegacao.LOGOUT;
import static br.com.simulado.security.constant.Navegacao.redirectTo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import br.com.simulado.models.Usuario;
import br.com.simulado.security.Logado;
import br.com.simulado.security.UsuarioSistema;

@Named
@SessionScoped
public class SessaoBean implements Serializable {

	private static final long serialVersionUID = -2390195761526411399L;

	@Inject
	private ExternalContext external;

	@Inject
	private HttpServletRequest request;

	@Inject
	private transient Logger logger;

	private Usuario usuarioLogado;

	@PostConstruct
	public void init() {
		try {

			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) external
					.getUserPrincipal();

			if (isAutenticado(auth)) {
				UsuarioSistema usuarioSistema = (UsuarioSistema) auth.getPrincipal();
				usuarioLogado = usuarioSistema.getUsuario();
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Forçando logout... " + e.getMessage());
			redirectTo(LOGOUT);
			invalidar();
		}

	}

	public String getPaginaInicial() {
		try {
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) external
					.getUserPrincipal();

			if (isAutenticado(auth))
				return getUsuarioLogado().getPaginaInicial();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Forçando logout... " + e.getMessage());
		}
		return LOGIN.url();
	}

	public void invalidar() {
		logout(request, null);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	@Logado
	@Produces
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public String getNomeUsuarioLogado() {
		String nome = "";
		try {
			nome = usuarioLogado.getNome();
			return nome.substring(0, nome.indexOf(' '));
		} catch (StringIndexOutOfBoundsException e) {
			return nome;
		} catch (NullPointerException e) {
			logger.warning("Erro ao recuperar nome do usuário logado");
			return nome;
		}
	}

	private boolean hasRole(String role) {
		return external.isUserInRole("ROLE_" + role);
	}

	private boolean isAutenticado(UsernamePasswordAuthenticationToken auth) {
		return auth != null && auth.getPrincipal() != null;
	}
	
	public Integer getAnoAtual() {
        return LocalDate.now().getYear();
    }

	public boolean hasRoleAdministrador() {
		return hasRole("ADMIN");
	}

	public boolean hasRoleUsuario() {
		return hasRole("USER");
	}
	
	public boolean hasAnyRoleSistema() {
		return hasRoleAdministrador() || hasRoleUsuario();
	}
	
	
}