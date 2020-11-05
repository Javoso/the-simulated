package br.com.simulado.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String URL_LOGIN = "/login.xhtml";
	private static final String ESQUECEU_SENHA = "/esqueceu-senha.xhtml";
	private static final String CADASTRAR_USUARIO = "/usuario/cadastrar-usuario.xhtml";
	private static final String SESSAO_EXPIRADA = "sessao-expirada.xhtml";

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}

	@Override
	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()

			.authorizeRequests()
				.antMatchers(SESSAO_EXPIRADA, "/403.xhtml", "/404.xhtml", "/500.xhtml").authenticated()
				.antMatchers(URL_LOGIN, ESQUECEU_SENHA, CADASTRAR_USUARIO, "/javax.faces.resource/**", "/resources/**").permitAll()
				
				
				.antMatchers("/categoria/**").hasRole("ADMIN")
				.antMatchers("/configuracoes/configuracao-email.xhtml").hasRole("ADMIN")
				.antMatchers("/conteudo/cadastrar-conteudo-apoio.xhtml").hasRole("ADMIN")
				.antMatchers("/conteudo/conteudo-apoio.xhtml").hasAnyRole("ADMIN","USER")
				.antMatchers("/dashboard/dashboard.xhtml").hasAnyRole("ADMIN","USER")
				.antMatchers("/questao/cadastrar-questao.xhtml").hasRole("ADMIN")
				.antMatchers("/questao/pesquisar-questao.xhtml").hasAnyRole("ADMIN")
				.antMatchers("/permissao/permissao.xhtml").hasRole("ADMIN")
				.antMatchers("/resposta/**").hasAnyRole("ADMIN","USER")
				.antMatchers("/simulado/**").hasAnyRole("ADMIN","USER")
				.antMatchers("/sub_categoria/**").hasRole("ADMIN")
				.antMatchers("/usuario/pesquisar-usuarios.xhtml").hasRole("ADMIN")
				.antMatchers("/usuario/alterar-senha.xhtml").hasAnyRole("ADMIN","USER")
				.anyRequest().authenticated()
				.and()

			.formLogin()
				.loginPage(URL_LOGIN)
				.failureUrl("/login.xhtml?invalid=true")
				.successHandler(new CustomAuthenticationHandler())
				.and()

			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.logoutSuccessUrl(URL_LOGIN)
				.and()

			.exceptionHandling()
				.accessDeniedPage("/403.xhtml");
	}
}