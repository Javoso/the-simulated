package br.com.simulado.controller.pesquisar;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.ConteudoApoio;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.service.CategoriaService;
import br.com.simulado.service.ConteudoApoioService;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
public class PesquisarConteudoApoioController extends AbstractController {

	private static final long serialVersionUID = -4718157721567462033L;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private SubCategoriaService subCategoriaService;

	@Inject
	private ConteudoApoioService conteudoApoioService;

	@Getter
	@Setter
	private Categoria categoria;

	@Getter
	@Setter
	private boolean fundamentosComputacao = false;

	@Getter
	@Setter
	private boolean matematica = false;

	@Getter
	@Setter
	private boolean tecnologiasComputacao = false;

	@Getter
	@Setter
	private List<SubCategoria> subcategorias = new ArrayList<>();

	@Getter
	@Setter
	private List<ConteudoApoio> conteudosDeApoio = new ArrayList<>();

	@Override
	public void init() {

		String nomeParamentro = getParamNameDecodificado("watch");

		if ("1".equals(nomeParamentro)) {
			this.fundamentosComputacao = true;
		}
		if ("3".equals(nomeParamentro)) {
			this.matematica = true;
		}
		if ("2".equals(nomeParamentro)) {
			this.tecnologiasComputacao = true;
		}

		if (nomeParamentro != null) {
			try {
				categoria = categoriaService.findById(nomeParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (nonNull(categoria)) {
			try {
				subcategorias = subCategoriaService.subCategoriasPorCategoria(categoria);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public List<ConteudoApoio> getConteudos(SubCategoria sub) {
		try {
			for (ConteudoApoio conteudoApoio : conteudoApoioService.conteudoApoiosPorSubCategoria(sub)) {
				System.out.println(conteudoApoio.idVideo());
			}
			return conteudoApoioService.conteudoApoiosPorSubCategoria(sub);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public String codificarCategoriaFundamento() {
		return getParamNameCodificado(1L);
	}

	public String codificarCategoriaMatematica() {
		return getParamNameCodificado(3L);
	}

	public String codificarCategoriaTecnologia() {
		return getParamNameCodificado(2L);
	}

}
