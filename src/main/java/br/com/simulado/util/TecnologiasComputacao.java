package br.com.simulado.util;

public enum TecnologiasComputacao {
	
	BANCO_DE_DADOS("Banco de Dados","https://www.youtube.com/embed/ZS7AcJM6q2A"),
	COMPILADORES("Compiladores","https://www.youtube.com/embed/etbUgnJgeW4"),
	COMPUTACAO_GRAFICA("Computação Gráfica","https://www.youtube.com/embed/BkqiNBa-Vyw"),
	ENGENHARIA_DE_SOFTWARE("Engenharia de Software","https://www.youtube.com/embed/kO1PSkzTsYc"),
	INTELIGENCIA_ARTIFICIAL("Inteligência Artificial","https://www.youtube.com/embed/Bcw5YZA-Avw"),
	PROCESSAMENTO_DE_IMAGENS("Processamento de Imagens","https://www.youtube.com/embed/TiwNPs5Ni5k"),
	REDES_DE_COMPUTADORES("Redes de Computadores","https://www.youtube.com/embed/efGBoJ-f_2Y"),
	SISTEMAS_DISTRIBUIDOS("Sistemas Distribuídos","https://www.youtube.com/embed/34RvRBXzvMo");
	
	private String descricao;
	
	private String link;
	
	TecnologiasComputacao(String descricao, String link) {
		this.descricao = descricao;
		this.link = link;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getId() {
		return link.replace("https://www.youtube.com/embed/", "").trim();
	}
	
}
