package br.com.simulado.models;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.com.simulado.models.constantes.TipoSimulado;
import br.com.simulado.util.AES;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SIMULADO")
@Entity(name = "simulado")
public class Simulado extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = 4492665817049626164L;

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DE_CRIACAO")
	private Date dataDeCriacao = new Date();

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;

	@Column(name = "NOME", length = 2000)
	private String nome;

	@ManyToMany
	@JoinTable(name = "SIMULADO_QUESTAO", joinColumns = { @JoinColumn(name = "SIMULADO_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "QUESTAO_ID") })
	private List<Questao> questoes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CRIADOR_ID")
	private Usuario criador;

	@Column(name = "TIPO_SIMULADO")
	@Enumerated(EnumType.STRING)
	private TipoSimulado tipoSimulado;

	@Override
	public boolean isAtivo() {
		return Objects.equals(status, true);
	}

	@Override
	public boolean isNova() {
		return !isCadastrada();
	}

	@Override
	public boolean isCadastrada() {
		return nonNull(id);
	}

	@Override
	public String codificarId() {
		return new AES().codificar(id.toString());
	}

	public void adicionaQuestoes(List<Questao> questoes) {
		questoes.forEach(this.questoes::add);
	}

	public void adicionaQuestao(Questao questao) {
		this.questoes.add(questao);
	}

	public boolean simuladoJaPossuiEssaQuestao(Questao questao) {
		return questoes.stream().anyMatch(q -> q.equals(questao));
	}

	public List<Questao> questoesDoSimuladoPorCategora(Categoria categoria) {
		if (possuiItens()) {
			return questoes.stream()
					.filter(q -> q.getCategoria().getNome().equalsIgnoreCase(categoria.getNome()))
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	public void removeQuestao(Questao questao) {
		this.questoes.remove(questao);
	}

	public boolean possuiItens() {
		return !questoes.isEmpty();
	}
}