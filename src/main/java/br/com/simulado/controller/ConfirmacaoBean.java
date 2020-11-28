package br.com.simulado.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.models.Usuario;
import br.com.simulado.service.UsuarioService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ConfirmacaoBean extends AbstractController {

	private static final long serialVersionUID = 4153961212119523087L;

	@Inject
	private UsuarioService service;

	@Getter
	@Setter
	private Usuario usuario = new Usuario();

	@Override
	public void init() {

		String id = getParamNameDecodificado("userid");

		if (id != null) {
			usuario = service.findById(id);
		}

	}

	public void mudarStatus() {
		try {
			service.mudarStatus(usuario);
			onSuccess("Informações validadas com sucesso!");
		} catch (Exception e) {
			onError("Erro ao alterar ao validar dados!" + e.getMessage());
		}
	}

}