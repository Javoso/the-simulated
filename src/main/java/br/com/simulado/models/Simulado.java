package br.com.simulado.models;

import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Simulado extends EntidadeGenerica<Long> implements Serializable{

	private static final long serialVersionUID = 4492665817049626164L;

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
	
}
