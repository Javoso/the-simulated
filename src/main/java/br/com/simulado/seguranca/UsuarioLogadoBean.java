//package br.com.simulado.seguranca;
//
//import java.io.Serializable;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.ExternalContext;
//import javax.inject.Inject;
//
//import br.com.simulado.models.Usuario;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * Classe controladora que verifica o usuário logado e fornece uma série de
// * verificações com base em suas permissões.
// *
// */
//@ManagedBean
//@SessionScoped
//public class UsuarioLogadoBean implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	@Getter
//	@Setter
//	@Inject
//	@Logado
//	private Usuario usuario;
//
//	@Inject
//	private ExternalContext externalContext;
//
//	/**
//	 * Verifica se o usuário logado possui a permissão parametrizada.
//	 * 
//	 * @param permissao Nome da permissão
//	 * @return boolean
//	 */
//	public boolean temPermissao(String permissao) {
//		return this.externalContext.isUserInRole(permissao);
//	}
//
//}