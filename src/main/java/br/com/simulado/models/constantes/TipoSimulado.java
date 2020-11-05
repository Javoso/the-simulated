package br.com.simulado.models.constantes;

public enum TipoSimulado {
	
	PADRAO("Padrão", 20),
	AREA("Área", 10),
	PERSONALIZADO("Personalizado", 10);
	
	private String nome;
	
	private int quantidadeQuestoes;
	
	TipoSimulado(String nome, int quantidadeQuestoes) {
		this.nome = nome;
		this.quantidadeQuestoes = quantidadeQuestoes;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getQuantidadeQuestoes() {
		return quantidadeQuestoes;
	}
}
