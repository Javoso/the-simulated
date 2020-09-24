package br.com.simulado.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.models.Categoria;
import br.com.simulado.models.constantes.TipoCategoria;
import br.com.simulado.service.CategoriaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 8140003464939423014L;
	
	@Inject
	private CategoriaService categoriaService;
	
	@Getter
	@Setter
	private int size;
	
	public TipoCategoria[] getTipos() {
		return TipoCategoria.values();
	}
	
	public List<Categoria> categoriaByTipo(TipoCategoria tipoCategoria){
		size = categoriaService.categoriasByTipo(tipoCategoria).size();
		
		return categoriaService.categoriasByTipo(tipoCategoria);
	}

}
