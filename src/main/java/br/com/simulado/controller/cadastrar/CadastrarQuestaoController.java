package br.com.simulado.controller.cadastrar;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Questao;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.CategoriaService;
import br.com.simulado.service.QuestaoService;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastrarQuestaoController extends AbstractController{

	private static final long serialVersionUID = 4195373868273708608L;

	@Inject
	private QuestaoService service;
	
	@Inject
	private SubCategoriaService serviceSub;

	@Inject
	private CategoriaService serviceCat;

	@Getter
	@Setter
	private Questao questao = new Questao(); 
	
	@Getter
	@Setter
	private Alternativa alternativa = new Alternativa();
	
	@Getter
	@Setter
	private Alternativa alternativaSelecionada = new Alternativa();
	
	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();
	
	@Getter
	@Setter
	private List<SubCategoria> subCategorias = new ArrayList<>();

	@Override
	public void init() {
		
		String idParamentro = getParamNameDecodificado("identificador");
		
		if(idParamentro != null) {
			try {
				questao = service.findById(idParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		categorias = serviceCat.categorias();
		subCategoriasPorCategoria();
	}
	
	public void subCategoriasPorCategoria() {
		try {
			subCategorias = serviceSub.subCategoriasPorCategoria(questao.getCategoria());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public boolean alternativaJaAdicionada() {
		try {
			return questao.alternativaJaAdicionada(alternativa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void adicionarAlternativa() {
		if(nonNull(questao.getAlternativas()) && nonNull(alternativa)) {
			try {
				questao.adicionarUmaAlternativa(alternativa);
				onSuccess("Alternativa adicionada com sucesso");
				limparAlternativa();
			} catch (Exception e) {
				onError(e.getMessage());
				limparAlternativa();
				e.printStackTrace();
			}
		}
	}
	
	public void editarAlternativa() {
		if(nonNull(questao.getAlternativas()) && nonNull(alternativa)) {
			try {
				questao.editarUmaAlternativa(alternativa);
				onSuccess("Alternativa editada com sucesso");
				limparAlternativa();
			} catch (Exception e) {
				onError(e.getMessage());
				limparAlternativa();
				e.printStackTrace();
			}
		}
	}
	
	public void removerAlternativa() {
		if(nonNull(questao.getAlternativas()) && nonNull(alternativaSelecionada)) {
			try {
				questao.removerUmaAlternativa(alternativaSelecionada);
				onSuccess("Alternativa removida com sucesso");
				limparAlternativa();
			} catch (Exception e) {
				onError(e.getMessage());
				limparAlternativa();
				e.printStackTrace();
			}
		}
	}
	
	
	public void save() {
		try {
			if(questao.questaoTemAlternativaCorreta()) {
				service.save(questao);
				onSuccessWithFlash("Questao cadastrada com sucesso");
			}
			//return "/questao/pesquisar-questao.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel cadastrar a questao, "+e.getMessage());
			e.printStackTrace();
		}
		limparCampos();
		//return null;
	}
	
	public void update() {
		try {
			service.update(questao);
			onSuccessWithFlash("Questao atualizada com sucesso");
			//return "/questao/pesquisar-questao.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel atualizar a questao, " + e.getMessage());
			e.printStackTrace();
		}
		limparCampos();
		//return null;
	}
	
	public String limparCampos() {
		questao = new Questao();
		return "/questao/cadastrar-questao.xhtml?faces-redirect=true";
	}
	
	public void limparAlternativa() {
		alternativa = new Alternativa();
	}

}
