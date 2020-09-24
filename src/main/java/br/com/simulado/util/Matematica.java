package br.com.simulado.util;

public enum Matematica {
	
	ALGEBRA_LINEAR("Álgebra Linear", "https://www.youtube.com/embed/TIs7FQKpXEw"),
	ANALISE_COMBINATORIA("Análise Combinatória","https://www.youtube.com/embed/6V3tvH6gw0Y"),
	CALCULO_DIFERENCIAL_E_INTEGRAL("Cálculo Diferencial e Integral","https://www.youtube.com/embed/WHNcAujGzQk"),
	GEOMETRIA_ANALITICA("Geometria Analítica","https://www.youtube.com/embed/ZJ5Aqwcx9f4"),
	LOGICA_MATEMATICA("Lágica Matemática","https://www.youtube.com/embed/PltqUuwR9ec"),
	MATEMATICA_DISCRETA("Matemática Discreta","https://www.youtube.com/embed/ib3F1c2oKpA"),
	PROBABILIDADE_E_ESTATISTICA("Probabilidade e Estatástica","https://www.youtube.com/embed/uNe81VZvqJg");
	
	private String descricao; 
	
	private String link; 
	
	Matematica(String descricao, String link){
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
