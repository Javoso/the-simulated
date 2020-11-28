package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;

import br.com.simulado.models.Usuario;
import lombok.Getter;
import lombok.Setter;

public class SimuladoRespondidoFilter implements Filter {

	
	@Getter
	@Setter
	private boolean status;
	
	@Getter
	@Setter
	private Usuario estudante;
	
	
	@Override
	public boolean temTitulo() {
		return false;
	}

	@Override
	public boolean temStatus() {
		return status;
	}
	
	public boolean temEstudante() {
		return nonNull(estudante);
	}

}
