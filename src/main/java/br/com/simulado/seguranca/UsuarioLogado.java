//package br.com.simulado.seguranca;
//
//import java.io.Serializable;
//
//import javax.enterprise.context.SessionScoped;
//import javax.enterprise.inject.Produces;
//import javax.faces.context.FacesContext;
//import javax.inject.Named;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//import br.com.simulado.models.Usuario;
//
//@Named
//@SessionScoped
//public class UsuarioLogado implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private Usuario usuario;
//
//	@Logado
//	@Produces
//	public Usuario getUsuario() {
//		if (usuario == null) {
//			UsuarioSistema usuarioSistema = null;
//
//			UsernamePasswordAuthenticationToken auth = getUserPrincipal();
//
//			if (isAutenticado(auth))
//				usuarioSistema = (UsuarioSistema) auth.getPrincipal();
//
//			usuario = usuarioSistema.getUsuario();
//		}
//
//		return usuario;
//	}
//
//	private UsernamePasswordAuthenticationToken getUserPrincipal() {
//		return (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext()
//				.getUserPrincipal();
//	}
//
//	private boolean isAutenticado(UsernamePasswordAuthenticationToken auth) {
//		return auth != null && auth.getPrincipal() != null;
//	}
//
//}