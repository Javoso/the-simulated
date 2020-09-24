package br.com.simulado.controller.cadastrar;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.Categoria;
import br.com.simulado.models.constantes.TipoCategoria;
import br.com.simulado.service.CategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastrarCategoriaController extends AbstractController{

	private static final long serialVersionUID = 4195373868273708608L;

	@Inject
	private CategoriaService service;

	@Getter
	@Setter
	private Categoria categoria = new Categoria(); 

	@Override
	public void init() {
		
		String idParamentro = getParamNameDecodificado("identificador");
		
		if(idParamentro != null) {
			try {
				categoria = service.findById(idParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public String save() {
		try {
			service.save(categoria);
			onSuccessWithFlash("Categoria cadastrada com sucesso");
			return "/categoria/pesquisar-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel cadastrar a categoria");
			e.printStackTrace();
		}
		limparCampos();
		return null;
	}
	
	public String update() {
		try {
			service.update(categoria);
			onSuccessWithFlash("Categoria atualizada com sucesso");
			return "/categoria/pesquisar-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel atualizar a categoria");
			e.printStackTrace();
		}
		limparCampos();
		return null;
	}
	
	public TipoCategoria[] getTipos() {
		return TipoCategoria.values();
	}

	public void limparCampos() {
		categoria = new Categoria();
	}

}
