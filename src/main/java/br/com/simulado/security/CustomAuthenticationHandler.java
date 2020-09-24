package br.com.simulado.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import br.com.simulado.infra.cdi.CDIServiceLocator;
import br.com.simulado.models.Usuario;

public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final String URL_MUDAR_SENHA = "/usuario/alterar-senha.xhtml";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
	
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		Usuario usuario = usuarioSistema.getUsuario();
		
		try {
			if (usuario.isMudarSenha()) 
				getRedirectStrategy().sendRedirect(request, response, URL_MUDAR_SENHA);
			else
				getRedirectStrategy().sendRedirect(request, response, usuario.getPaginaInicial());
		} catch (NullPointerException e) {
			Logger logger = CDIServiceLocator.getBean(Logger.class);
			logger.severe("Erro ao redirecionar usuário " + usuario.getEmail());
		}
		
	}

}