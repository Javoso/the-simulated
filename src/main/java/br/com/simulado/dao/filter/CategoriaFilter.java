package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;

import br.com.simulado.models.constantes.TipoCategoria;
import lombok.Getter;
import lombok.Setter;

public class CategoriaFilter implements Filter, Serializable{
	
	private static final long serialVersionUID = 8232373049360490282L;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private boolean status;
	
	@Getter
	@Setter
	private TipoCategoria tipoCategoria;
	
	
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

}
