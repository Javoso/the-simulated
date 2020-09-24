package br.com.simulado.controller.pesquisar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.dao.filter.CategoriaFilter;
import br.com.simulado.models.Categoria;
import br.com.simulado.service.CategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisarCategoriaController extends AbstractController {

	private static final long serialVersionUID = -5580915806129341373L;

	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();

	@Getter
	@Setter
	private Categoria selecionado = new Categoria();

	@Inject
	private CategoriaService service;

	@Getter
	@Setter
	private CategoriaFilter filter = new CategoriaFilter();

	@Override
	public void init() {
		if (isNotPostback()) {
			initValues();
		}
	}

	private void initValues() {
		categorias = service.categorias();
	}

	public void pesquisar() {
		categorias = service.pesquisar(filter);
		if (categorias.isEmpty()) {
			onError("Nenhuma categoria foi encontrada");
			filter = null;
			initValues();
		}
	}

	public void resetFilterNomeCategoria() {
		this.filter.setNome(null);
		pesquisar();
	}

	public void resetFilterTipoCategoria() {
		this.filter.setTipoCategoria(null);
		pesquisar();
	}

	public void mudarStatus() {
		try {
			service.mudarStatus(selecionado);
			onSuccess("Status alterado com sucesso!");
			pesquisar();
		} catch (Exception e) {
			onError("Erro ao alterar o status da categoria!" + e.getMessage());
		}
	}

}
