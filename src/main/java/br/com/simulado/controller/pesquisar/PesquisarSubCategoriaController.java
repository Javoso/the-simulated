package br.com.simulado.controller.pesquisar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.dao.filter.SubCategoriaFilter;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisarSubCategoriaController extends AbstractController {

	private static final long serialVersionUID = 5190535209912752964L;

	@Getter
	@Setter
	private List<SubCategoria> subCategorias = new ArrayList<>();

	@Getter
	@Setter
	private SubCategoria selecionado = new SubCategoria();

	@Inject
	private SubCategoriaService service;

	@Getter
	@Setter
	private SubCategoriaFilter filter = new SubCategoriaFilter();

	@Override
	public void init() {
		if (isNotPostback()) {
			initValues();
		}
	}

	private void initValues() {
		subCategorias = service.subCategorias();
	}

	public void pesquisar() {
		subCategorias = service.pesquisar(filter);
		if (subCategorias.isEmpty()) {
			onError("Nenhuma subcategoria foi encontrada");
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
	
	public void resetFilterCategoria() {
		this.filter.setCategoria(null);
		pesquisar();
	}

	public void mudarStatus() {
		try {
			service.mudarStatus(selecionado);
			onSuccess("Status alterado com sucesso!");
			pesquisar();
		} catch (Exception e) {
			onError("Erro ao alterar o status da subcategoria!" + e.getMessage());
		}
	}

}
