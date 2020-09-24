package br.com.simulado.controller.cadastrar;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named
@ViewScoped
public class CadastrarConteudoApoioController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 8679814842172904863L;

	@Inject
	private SubCategoriaService serviceSub;

	@Inject
	private CategoriaService service;

	@Inject
	private ConteudoApoioService conteudoService;

	@Getter
	@Setter
	private Categoria categoria = new Categoria();

	@Getter
	@Setter
	private ConteudoApoio conteudo = new ConteudoApoio();

	@Getter
	@Setter
	private List<SubCategoria> subCategorias = new ArrayList<>();

	@Getter
	@Setter
	private List<Categoria> categorias = new ArrayList<>();

	@Override
	public void init() {
		String idParamentro = getParamNameDecodificado("identificador");

		if (idParamentro != null) {
			try {
				conteudo = conteudoService.findById(idParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		categorias = service.categorias();
	}

	public void subCategoriasPorCategoria() {
		try {
			subCategorias = serviceSub.subCategoriasPorCategoria(categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			conteudoService.save(conteudo);
			onSuccessWithFlash("conteúdo de apoio salvo com sucesso");
			// return "/sub_categoria/pesquisar-sub-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel salvar a subcategoria");
			e.printStackTrace();
		}
		limparCampos();
		// return null;
	}

	public void update() {
		try {
			conteudoService.update(conteudo);
			onSuccessWithFlash("Conteudo de apoio atualizada com sucesso");
			// return "/sub_categoria/pesquisar-sub-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel atualizar a subcategoria");
			e.printStackTrace();
		}
		limparCampos();
		// return null;
	}
	public void limparCampos() {
		conteudo = new ConteudoApoio();
		categoria = new Categoria();
	}
}
