package br.com.simulado.models;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import br.com.simulado.models.constantes.TipoCategoria;
import br.com.simulado.util.AES;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name="CATEGORIA")
@Entity(name="categoria")
public class Categoria extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = -7398200755821797212L;
	
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

	@Column(name="CATEGORIA_NOME")
	private String nome;
	
	@Column(name="CATEGORIA_DESCRICAO")
	private String descricao;
	
	@Column(name="CATEGORIA_STYLE")
	private String style;
	
	@Column(name="CATEGORIA_ICONE")
	private String icone;
	
	@Column(name="TIPO_CATEGORIA")
	@Enumerated(EnumType.STRING)
	private TipoCategoria tipoCategoria;

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
	
}
