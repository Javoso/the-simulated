package br.com.simulado.controller.pesquisar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.dao.filter.QuestaoFilter;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Questao;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.AlternativaService;
import br.com.simulado.service.QuestaoService;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class PesquisarQuestaoController extends AbstractController {

	private static final long serialVersionUID = -5580915806129341373L;

	@Getter
	@Setter
	private List<Questao> questoes = new ArrayList<>();
	
	@Getter
	@Setter
	private List<SubCategoria> subCategorias = new ArrayList<>();

	@Getter
	@Setter
	private Questao selecionada = new Questao();
	
	@Getter
	@Setter
	private String linkImagem;

	@Inject
	private QuestaoService service;
	
	@Getter
	@Setter
	private Alternativa alternativaSelecionada = new Alternativa();

	@Inject
	private AlternativaService alternativaService;
	
	@Inject
	private SubCategoriaService subService;

	@Getter
	@Setter
	private QuestaoFilter filter = new QuestaoFilter();

	@Override
	public void init() {
		if (isNotPostback()) {
			initValues();
		}
	}

	private void initValues() {
		questoes = service.questoes();
	}
	
	
	public void pesquisarSubCategoria() {
		try {
			subCategorias = subService.subCategoriasPorCategoria(filter.getCategoria());
		} catch (Exception e) {
			e.printStackTrace();
			onError(e.getMessage());
		}
	}

	public void pesquisar() {
		questoes = service.pesquisar(filter);
		if (questoes.isEmpty()) {
			onError("Nenhuma questao foi encontrada");
			filter = null;
			initValues();
		}
	}
	
	public void resetFilterCategoria() {
		this.filter.setCategoria(null);
		pesquisar();
	}
	
	public void resetFilterSubCategoria() {
		this.filter.setSubCategoria(null);
		pesquisar();
	}

	public void mudarStatus() {
		try {
			service.mudarStatus(selecionada);
			onSuccess("Status alterado com sucesso!");
			pesquisar();
		} catch (Exception e) {
			onError("Erro ao alterar o status da questao!" + e.getMessage());
		}
	}
	
	public void mudarStatusAlternativa() {
		try {
			alternativaService.mudarStatus(alternativaSelecionada);
			onSuccess("Status alterado com sucesso!");
		} catch (Exception e) {
			onError("Erro ao alterar o status da alternativa!" + e.getMessage());
		}
	}

}
