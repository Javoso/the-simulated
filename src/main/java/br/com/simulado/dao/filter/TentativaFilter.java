package br.com.simulado.dao.filter;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.Objects;

import br.com.simulado.models.Simulado;
import br.com.simulado.models.Usuario;
import lombok.Getter;
import lombok.Setter;

public class TentativaFilter implements Filter, Serializable{

	private static final long serialVersionUID = -5100945566128195177L;

	@Getter
	@Setter
	private Simulado simulado;
	
	@Getter
	@Setter
	private Usuario estudante;
	
	@Getter
	@Setter
	private boolean status;
	
	public boolean temSimulado() {
		return nonNull(getSimulado());
	}
	
	public boolean temEstudante() {
		return nonNull(getEstudante());
	}
	
	@Override
	public boolean temStatus() {
		return Objects.equals(status, true);
	}

	@Override
	public boolean temTitulo() {
		return false;
	}
	
}
