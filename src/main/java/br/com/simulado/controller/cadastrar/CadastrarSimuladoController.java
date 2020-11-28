package br.com.simulado.controller.cadastrar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.dao.filter.QuestaoFilter;
import br.com.simulado.models.Questao;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.constantes.TipoSimulado;
import br.com.simulado.security.Logado;
import br.com.simulado.service.QuestaoService;
import br.com.simulado.service.SimuladoService;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastrarSimuladoController extends AbstractController{

	private static final long serialVersionUID = 4466325970468410146L;

	@Inject
	private SimuladoService service;

	@Getter
	@Setter
	private Simulado simulado = new Simulado(); 
	
	@Getter
	@Setter
	private Questao selecionada = new Questao();
	
	@Getter
	@Setter
	private String linkImagem;
	
	@Getter
	@Setter
	private List<Questao> questoes = new ArrayList<>();
	
	@Getter
	@Setter
	private List<SubCategoria> subCategorias = new ArrayList<>();
	
	@Inject
	private SubCategoriaService subService;
	
	@Inject
	private QuestaoService questaoService;
	
	@Getter
	@Setter
	private QuestaoFilter filter = new QuestaoFilter();
	
	@Inject
	@Logado
	private Usuario usuarioLogado;
	
	@Override
	public void init() {
		
		String idParamentro = getParamNameDecodificado("identificador");
		
		if(idParamentro != null) {
			try {
				simulado = service.findById(idParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			simulado.setCriador(usuarioLogado);
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
	
	public void pesquisar() {
		questoes = questaoService.pesquisar(filter);
		if (questoes.isEmpty()) {
			onError("Nenhuma questao foi encontrada");
			filter = null;
		}
	}
	
	public void adicionaQuestaoNoSimulado(Questao questao) {
		simulado.adicionaQuestao(questao);
		onSuccess("Questão adionada ao simulado");
		updateComponents("msg-dlg");
	}
	
	public void removerQuestaoDoSimulado(Questao questao) {
		simulado.removeQuestao(questao);
		onSuccess("Questão removida do simulado");
		updateComponents("msg-dlg");
	}
	
	public boolean pertenceAoSimulado(Questao questao) {
		return simulado.simuladoJaPossuiEssaQuestao(questao);
	}
	

	public void pesquisarSubCategoria() {
		try {
			subCategorias = subService.subCategoriasPorCategoria(filter.getCategoria());
		} catch (Exception e) {
			e.printStackTrace();
			onError(e.getMessage());
		}
	}
	
	public void save() {
		try {
			service.save(simulado);
			onSuccessWithFlash("Simulado cadastrada com sucesso");
			//return "/simulado/pesquisar-simulado.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel cadastrar a simulado");
			e.printStackTrace();
		}
		limparCampos();
		//return null;
	}
	
	public String update() {
		try {
			service.update(simulado);
			onSuccessWithFlash("Simulado atualizada com sucesso");
			return "/simulado/pesquisar-simulado.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel atualizar a simulado");
			e.printStackTrace();
		}
		limparCampos();
		return null;
	}
	
	public TipoSimulado[] getTipos() {
		return TipoSimulado.values();
	}

	public void limparCampos() {
		simulado = new Simulado();
	}

}
