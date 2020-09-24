package br.com.simulado.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.models.Alternativa;
import br.com.simulado.service.AlternativaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class AlternativaController extends AbstractController {

	private static final long serialVersionUID = -5021908891916022463L;

	@Getter
	@Setter
	private Alternativa selecionada = new Alternativa();

	@Inject
	private AlternativaService service;
	
	
	@Override
	public void init() {
	
	}


	public void mudarStatus() {
		try {
			service.mudarStatus(selecionada);
			onSuccess("Status alterado com sucesso!");
		} catch (Exception e) {
			onError("Erro ao alterar o status da alternativa!" + e.getMessage());
		}
	}

}
