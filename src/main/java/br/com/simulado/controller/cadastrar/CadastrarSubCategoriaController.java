package br.com.simulado.controller.cadastrar;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.controller.AbstractController;
import br.com.simulado.models.SubCategoria;
import br.com.simulado.models.constantes.TipoCategoria;
import br.com.simulado.service.SubCategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class CadastrarSubCategoriaController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 3971536931149724404L;

	@Inject
	private SubCategoriaService service;

	@Getter
	@Setter
	private SubCategoria subCategoria = new SubCategoria();

	@Override
	public void init() {
		
		String idParamentro = getParamNameDecodificado("identificador");

		if (idParamentro != null) {
			try {
				subCategoria = service.findById(idParamentro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		try {
			service.save(subCategoria);
			onSuccessWithFlash("Subcategoria salva com sucesso");
			//return "/sub_categoria/pesquisar-sub-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel salvar a subcategoria");
			e.printStackTrace();
		}
		limparCampos();
		//return null;
	}
	
	
	public String update() {
		try {
			service.update(subCategoria);
			onSuccessWithFlash("Subcategoria atualizada com sucesso");
			return "/sub_categoria/pesquisar-sub-categoria.xhtml?faces-redirect=true";
		} catch (Exception e) {
			onError("Não foi possivel atualizar a subcategoria");
			e.printStackTrace();
		}
		limparCampos();
		return null;
	}
	
	public TipoCategoria[] getTipos() {
		return TipoCategoria.values();
	}
	
	public void limparCampos() {
		subCategoria = new SubCategoria();
	}

}
