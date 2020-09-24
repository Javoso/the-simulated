package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;

import br.com.simulado.models.Categoria;
import br.com.simulado.models.SubCategoria;
import lombok.Getter;
import lombok.Setter;

public class QuestaoFilter implements Filter, Serializable {

	private static final long serialVersionUID = -7765374604208597550L;

	@Getter
	@Setter
	private String enunciado;

	@Getter
	@Setter
	private boolean status;

	@Getter
	@Setter
	private Categoria categoria;

	@Getter
	@Setter
	private SubCategoria subCategoria;

	@Override
	public boolean temTitulo() {
		return isNotBlank(getEnunciado());
	}

	@Override
	public boolean temStatus() {
		return status;
	}

	public boolean temCategoria() {
		return nonNull(getCategoria());
	}

	public boolean temSubCategoria() {
		return nonNull(getSubCategoria());
	}

}
