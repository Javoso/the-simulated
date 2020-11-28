package br.com.simulado.models;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.simulado.util.AES;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "TENTATIVA")
@Entity(name = "tentativa")
public class Tentativa extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = -7416763791144885578L;

	@Id
	@Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SIMULADO_ID")
	private Simulado simulado;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "TENTATIVA__RESPOSTA", joinColumns = {
			@JoinColumn(name = "TENTATIVA_ID") }, inverseJoinColumns = { @JoinColumn(name = "RESPOSTA_ID") })
	private List<Resposta> respostas = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTUDANTE_ID")
	private Usuario estudante;

	@Column(name = "DATA_DE_RESPOSTA")
	private Date tempoDeResposta = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DE_CRIACAO")
	private Date dataDeCriacao = new Date();

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;

	@Override
	public boolean isAtivo() {
		return Objects.equals(status, true);
	}

	@Override
	public boolean isNova() {
		return isNull(id);
	}

	@Override
	public boolean isCadastrada() {
		return !isNova();
	}

	@Override
	public String codificarId() {
		return new AES().codificar(getId().toString());
	}

	@Transient
	public Resposta respostaPraQuestao(Questao questao) {
		return this.respostas.stream().filter(r -> r.getQuestaoSelecionada().equals(questao)).findFirst().get();
	}
	
	@Transient
	public void adicionarVariasRespostas(List<Resposta> respostas) {
		this.respostas.addAll(respostas);
	}

	@Transient
	public void adicionarUmaResposta(Resposta resposta) throws Exception {
		resposta.setSimulado(simulado);
		this.respostas.add(resposta);
	}

	@Transient
	public boolean possuiRespostas() throws Exception {
	if (!respostas.isEmpty())
		return true;
	else
		throw new Exception("Lista de respostas vazia");
	}

}
