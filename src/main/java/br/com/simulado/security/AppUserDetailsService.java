package br.com.simulado.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.simulado.infra.cdi.CDIServiceLocator;
import br.com.simulado.models.Usuario;
import br.com.simulado.service.UsuarioService;

public class AppUserDetailsService implements UserDetailsService {
	

	@Override
	public UserDetails loadUserByUsername(String email) {
		System.out.println(email);
		UsuarioService usuarioService = CDIServiceLocator.getBean(UsuarioService.class);
		Usuario usuario = usuarioService.login(email);
		return new UsuarioSistema(usuario, getPermissoes(usuario));

	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes()
		.forEach(a -> authorities.add(new SimpleGrantedAuthority("ROLE_" + a.getNome().toUpperCase())));
		return authorities;
	}
}