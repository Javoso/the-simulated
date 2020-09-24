package br.com.simulado.models.constantes;

public enum TipoCategoria {
	
	COMPUTACAO("Computação","fa fa-laptop"),
	MATEMATICA("Matemática","fa fa-calculator");
	
	private String nome;
	
	private String icon;
	
	TipoCategoria(String nome, String icon) {
		this.nome = nome;
		this.icon = icon;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getIcon() {
		return icon;
	}
}
