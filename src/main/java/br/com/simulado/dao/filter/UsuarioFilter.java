package br.com.simulado.dao.filter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.util.Objects;

import br.com.simulado.models.Permissao;
import lombok.Getter;
import lombok.Setter;

public class UsuarioFilter implements Filter, Serializable{
	
	private static final long serialVersionUID = 8232373049360490282L;
	
	@Getter
	@Setter
	private String nome;
	
	@Getter
	@Setter
	private boolean status;
	
	@Getter
	@Setter
	private Permissao permissao;
	
	@Override
	public boolean temTitulo() {
		return isNotBlank(getNome());
	}
	
	@Override
	public boolean temStatus() {
		return Objects.equals(status, true);
	}
	
	public boolean temPermissao() {
		return Objects.nonNull(permissao);
	}
	
}
