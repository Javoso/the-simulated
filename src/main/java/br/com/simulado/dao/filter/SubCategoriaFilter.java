package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;

import org.apache.commons.lang3.EnumUtils;

import br.com.simulado.models.Categoria;
import br.com.simulado.models.constantes.TipoCategoria;
import lombok.Getter;
import lombok.Setter;

public class SubCategoriaFilter implements Filter, Serializable{
	
	private static final long serialVersionUID = -7765374604208597550L;

	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private boolean status;
	
	@Getter
	@Setter
	private TipoCategoria tipoCategoria = EnumUtils.getEnum(TipoCategoria.class, "TipoCategoria");
	
	@Getter
	@Setter
	private Categoria categoria;
	
	
	@Override
	public boolean temTitulo() {
		return isNotBlank(getNome());
	}
	
	@Override
	public boolean temStatus() {
		return status;
	}
	
	public boolean temTipo() {
		return nonNull(getTipoCategoria());
	}
	
	public boolean temCategoria() {
		return nonNull(getCategoria());
	}

}
