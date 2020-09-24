package br.com.simulado.dao.filter;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class AlternativaFilter implements Filter, Serializable{
	
	private static final long serialVersionUID = 8232373049360490282L;
	
	@Getter
	@Setter
	private String enunciado;
	
	@Getter
	@Setter
	private boolean status;
	
	
	@Override
	public boolean temTitulo() {
		return isNotBlank(getEnunciado());
	}
	
	@Override
	public boolean temStatus() {
		return Objects.equals(status, true);
	}
	
}
