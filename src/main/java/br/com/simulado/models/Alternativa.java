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
@Table(name = "ALTERNATIVA")
@Entity(name="alternativa")
public class Alternativa extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = -7391326107937508194L;

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_DE_CRIACAO")
	private Date dataDeCriacao = new Date();

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;
	
	@Column(name = "ENUNCIADO", length = 2000)
	private String enunciado;
	
	@Type(type = "true_false")
	@Column(name = "CORRETA")
	private Boolean correta = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "QUESTAO_ID")
	private Questao questao = new Questao();

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
	
	public boolean eCorreta() {
		return correta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enunciado == null) ? 0 : enunciado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alternativa other = (Alternativa) obj;
		if (enunciado == null) {
			if (other.enunciado != null)
				return false;
		} else if (!enunciado.equals(other.enunciado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
