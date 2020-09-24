package br.com.simulado.models;
import java.util.Date;


public abstract class EntidadeGenerica<ID> {
	
	public abstract void setId(ID id);
	
	public abstract ID getId();
	
	public abstract void setDataDeCriacao(Date dataDeCriacao);
	
	public abstract Date getDataDeCriacao();
	
	public abstract void setStatus(Boolean status);
	
	public abstract Boolean getStatus();
	
	public abstract boolean isAtivo();
	
	public abstract boolean isNova();
	
	public abstract boolean isCadastrada();
	
	public abstract String codificarId();

}
