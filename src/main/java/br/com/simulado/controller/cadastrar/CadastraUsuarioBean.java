package br.com.simulado.controller.cadastrar;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.dao.PermissaoDAO;
import br.com.simulado.models.Permissao;
import br.com.simulado.models.Usuario;
import br.com.simulado.service.UsuarioService;
import br.com.simulado.service.exception.RegistroExistenteException;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastraUsuarioBean extends AbstractController {

	private static final long serialVersionUID = -6795570219946581473L;

	@Inject
	private UsuarioService usuarioService;

	@Getter
	@Setter
	private String confirmarSenha = "";

	@Getter
	@Setter
	private boolean aceteiTermos = false;

	@Inject
	private PermissaoDAO permissao;

	@Getter
	@Setter
	private Usuario usuario = new Usuario();

	@Override
	public void init() {
		String id = getParamNameDecodificado("id");

		if (id != null)
			usuario = usuarioService.findById(id);
	}

	public void salvar() {
		Permissao per = permissao.findByNome("USER");
		try {
			if (usuario.getSenha().equalsIgnoreCase(confirmarSenha) && aceteiTermos) {
				usuario.adicionar(per);
				usuario.setStatus(false);
				usuarioService.persist(usuario);
				onSuccessWithFlash("Usuário salvo com sucesso!");
				redirect("/dados-validados.xhtml");
			} else {
				onError("As senhas são diferentes");
			}
		} catch (PersistenceException e) {
			onError("Já existe um usuário cadastrado");
			e.printStackTrace();
		} catch (Exception e) {
			onError("Erro ao salvar o usuário!");
			logger.severe(e.getMessage());
		}
	}

	public String atualizar() {
		try {
			merge();
			onSuccessWithFlash("Usuário atualizado com sucesso!");
			return "/usuario/pesquisar-usuarios.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Erro ao atualizar o usuário!");
		}
		return null;
	}

	private void merge() throws RegistroExistenteException {
		usuarioService.merge(usuario);
	}

}