package br.com.simulado.controller.cadastrar;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.Alternativa;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.Questao;
import br.com.simulado.models.Resposta;
import br.com.simulado.models.Simulado;
import br.com.simulado.models.SimuladoRespondido;
import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.constantes.TipoSimulado;
import br.com.simulado.security.Logado;
import br.com.simulado.service.CategoriaService;
import br.com.simulado.service.SimuladoRespondidoService;
import br.com.simulado.service.SimuladoService;
import br.com.simulado.service.TentativaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastrarTentativaController extends AbstractController {

	private static final long serialVersionUID = 6677870483687358595L;

	@Inject
	private SimuladoService service;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private TentativaService tentativaService;

	@Inject
	private SimuladoRespondidoService simuladoRespondidoService;

	@Getter
	@Setter
	private Simulado simulado = new Simulado();

	@Getter
	@Setter
	private List<Simulado> simulados = new ArrayList<>();

	@Getter
	@Setter
	@Inject
	@Logado
	private Usuario estudanteLogado;

	@Getter
	@Setter
	private Resposta resposta;

	@Getter
	@Setter
	private Tentativa tentativa = new Tentativa();

	@Getter
	@Setter
	private Alternativa alternativa = new Alternativa();

	@Getter
	@Setter
	private List<Resposta> respostas = new ArrayList<>();

	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();

	@Getter
	@Setter
	private boolean visibleModal = true;

	@Override
	public void init() {

		String idParamentro = getParamNameDecodificado("simuladoID");

		if (idParamentro != null) {
			try {
				simulado = service.findById(idParamentro);
				visibleModal = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		categorias = categoriaService.categorias();
		simulados = service.simulados();
	}

	public void adicionarResposta(int index, Questao questao) {
		resposta = new Resposta(estudanteLogado, simulado, questao, alternativa);
		if (respostaJaAdicionada(resposta.getQuestaoSelecionada())) {
			this.respostas.set(index, resposta);
		} else {
			this.respostas.add(resposta);
		}
		System.out.println(respostas.size());
	}

	public boolean respostaJaAdicionada(Questao questao) {
		return this.respostas.stream().anyMatch(resposta -> resposta.getQuestaoSelecionada().equals(questao));
	}

	public void save() {
		try {
			if (!respostas.isEmpty()) {
				tentativa.setRespostas(respostas);
				tentativa.setEstudante(estudanteLogado);
				tentativa.setSimulado(simulado);
				tentativa = tentativaService.salvar(tentativa);

				simuladoRespondidoService.save(new SimuladoRespondido(simulado, estudanteLogado, tentativa));

				onSuccessWithFlash("Respostas cadastrada com sucesso");

				String url = "/tentativa/tentativa.xhtml?simuladoId=" + simulado.codificarId() + "&userId="
						+ estudanteLogado.codificarId() + "&tentativaId=" + tentativa.codificarId()+"";

				redirect(url);
			}
		} catch (Exception e) {
			onError("Não foi possivel cadastrar as respostas, cause: " + e.getMessage());
			e.printStackTrace();
		}
		limparCampos();
	}

	public String update() {
		try {
			tentativaService.update(tentativa);
			onSuccessWithFlash("Simulado atualizada com sucesso");
			return "/simulado/pesquisar-simulado.xhtml";
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
		respostas = new ArrayList<>();
		tentativa = new Tentativa();
		alternativa = new Alternativa();
		redirect("/dashboard/dashboard.xhtml");
	}

}
