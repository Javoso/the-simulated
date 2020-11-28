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
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Table(name = "SUB_CATEGORIA")
@Entity(name = "subCategoria")
public class SubCategoria extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = 3446684748142897229L;

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_DE_CRIACAO")
	@Temporal(TemporalType.DATE)
	private Date dataDeCriacao = new Date();

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;

	@Column(name = "SUB_CATEGORIA_NOME")
	private String nome;

	@Column(name = "SUB_CATEGORIA_DESCRICAO")
	private String descricao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORIA_ID")
	private Categoria categoria = new Categoria();

	@Override
	public String codificarId() {
		return new AES().codificar(getId().toString());
	}

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
	public String toString() {
		return getNome();
	}
	
	

}
