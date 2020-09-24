package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;

import br.com.simulado.models.SubCategoria;
import lombok.Getter;
import lombok.Setter;

public class ConteudoApoioFilter implements Filter, Serializable{
	
	private static final long serialVersionUID = -7765374604208597550L;

	@Getter
	@Setter
	private String titulo;
	
	@Getter
	@Setter
	private boolean status;
	
	@Getter
	@Setter
	private SubCategoria subCategoria;
	
	
	@Override
	public boolean temTitulo() {
		return isNotBlank(getTitulo());
	}
	
	@Override
	public boolean temStatus() {
		return status;
	}
	
	public boolean temSubCategoria() {
		return nonNull(getSubCategoria());
	}

}
