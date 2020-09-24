//package br.com.simulado.seguranca;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	private static final String LOGIN_XHTML_INVALID_TRUE = "/login.xhtml?invalid=true";
//	private static final String RESOURCES = "/resources/**";
//	private static final String JAVAX_FACES_RESOURCE = "/javax.faces.resource/**";
//	private static final String LOGOUT = "/logout";
//	private static final String PAGINA_500_XHTML = "/500.xhtml";
//	private static final String PAGINA_DASHBOARD_XHTML = "/dashboard/dashboard.xhtml";
//	private static final String PAGINA_404_XHTML = "/404.xhtml";
//	private static final String PAGINA_403_XHTML = "/403.xhtml";
//	private static final String LOGIN_XHTML = "/login.xhtml";
//	private static final String URL_ESQUECEU = "/esqueceu-senha.xhtml";
//
//	@Override
//	@Bean
//	public AppUserDetailsService userDetailsService() {
//		return new AppUserDetailsService();
//	}
//
//	@Bean
//	public Md5PasswordEncoder passwordEncoder() {
//		return new Md5PasswordEncoder();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
//		jsfLoginEntry.setLoginFormUrl(LOGIN_XHTML);
//		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
//
//		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
//		jsfDeniedEntry.setLoginPath(PAGINA_403_XHTML);
//		jsfDeniedEntry.setContextRelative(true);
//		
//		http.csrf().disable().headers().frameOptions().sameOrigin().and()
//
//				.authorizeRequests()
//				
//				.antMatchers(
//						JAVAX_FACES_RESOURCE, 
//						LOGIN_XHTML, 
//						PAGINA_500_XHTML,
//						RESOURCES,
//						URL_ESQUECEU)
//				.permitAll()
//				
//				.antMatchers(
//						PAGINA_DASHBOARD_XHTML, 
//						PAGINA_403_XHTML, 
//						PAGINA_404_XHTML)
//				.authenticated() 
//						
//				.antMatchers("/categoria/**").hasRole("ADMIN")
//				.antMatchers("/configuracoes/configuracao-email.xhtml").hasRole("ADMIN")
//				.antMatchers("/conteudo/cadastrar-conteudo-apoio.xhtml").hasRole("ADMIN")
//				.antMatchers("/conteudo/conteudo-apoio.xhtml").hasAnyRole("ADMIN","USER")
//				.antMatchers(PAGINA_DASHBOARD_XHTML).hasAnyRole("ADMIN","USER")
//				.antMatchers("/permissao/permissao.xhtml").hasRole("ADMIN")
//				.antMatchers("/resposta/**").hasAnyRole("ADMIN","USER")
//				.antMatchers("/simulado/**").hasAnyRole("ADMIN","USER")
//				.antMatchers("/sub_categoria/**").hasRole("ADMIN")
//				.antMatchers("/usuario/pesquisar-usuario.xhtml").hasRole("ADMIN")
//				.antMatchers("/usuario/cadastrar-usuario.xhtml").hasAnyRole("ADMIN","USER")
//				.antMatchers("/usuario/pesquisar-usuarios.xhtml").hasRole("ADMIN")
//				.antMatchers("/usuario/alterar-senha.xhtml").hasAnyRole("ADMIN","USER")
//				.anyRequest().authenticated()
//
//				.and()
//				
//				.formLogin().loginPage(LOGIN_XHTML).failureUrl(LOGIN_XHTML_INVALID_TRUE).and()
//
//				.logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT)).and()
//
//				.exceptionHandling().accessDeniedPage(PAGINA_403_XHTML).authenticationEntryPoint(jsfLoginEntry)
//				
//				.accessDeniedHandler(jsfDeniedEntry);
//
//	}
//
//}