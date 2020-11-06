package br.com.simulado.models;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "RESPOSTA")
@Entity(name = "resposta")
public class Resposta extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = 1840996085436215203L;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ESTUDANTE_ID")
	private Usuario estudante;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SIMULADO_ID")
	private Simulado simulado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "QUESTAO_ID")
	private Questao questaoSelecionada;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ALTERNATIVA_ID")
	private Alternativa alternativaEscolhida;

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

}
