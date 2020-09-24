package br.com.simulado.util;

public enum FundamentosComputacao {

	ANALISE_DE_ALGORITMOS("Análise de Algoritmos", "https://www.youtube.com/embed/186lPQE-h64"),
	
	ALGORITMOS_E_ESTRUTURA_DE_DADOS("Algoritmos e Estrutura de Dados", "https://www.youtube.com/embed/e8tdtnDmUpE"),
	
	ARQUITETURA_E_ORGANIZACAOO_DE_COMPUTADORES("Arquitetura e Organização de Computadores",
			"https://www.youtube.com/embed/T91EcagcyMo"),
	
	CIRCUITOS_DIGITAIS("Circuitos Digitais", "https://www.youtube.com/embed/aYVz0l3ZMWc"),
	
	LINGUAGENS_DE_PROGRAMACAO("Linguagens de Programação", "https://www.youtube.com/embed/KsDQN3Yw-HU"),
	
	LINGUAGENS_FORMAIS_AUTOMATOS_E_COMPUTABILIDADE("Linguagens Formais, Autômatos e Computabilidade",
			"https://www.youtube.com/embed/rOghFMVbVoI"),
	
	ORGANIZACAO_DE_ARQUIVOS_E_DADOS("Organização de Arquivos e Dados", "https://www.youtube.com/embed/7Cl-fqdEqUQ"),
	
	SISTEMAS_OPERACIONAIS("Sistemas Operacionais", "https://www.youtube.com/embed/CCHZ_06DoEA"),
	
	TECNICAS_DE_PROGRAMACAO("Técnicas de Programação", "https://www.youtube.com/embed/8mei6uVttho"),
	
	TEORIA_DOS_GRAFOS("Teoria dos Grafos", "https://www.youtube.com/embed/Frmwdter-vQ");

	private String descricao;

	private String link;

	FundamentosComputacao(String descricao, String link) {
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
