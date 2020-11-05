package br.com.simulado.models;

import static java.util.Objects.isNull;

import java.io.Serializable;
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
@Table(name = "QUESTAO")
@Entity(name = "questao")
public class Questao extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = -641501138140666483L;

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_DE_CRIACAO")
	private Date dataDeCriacao = new Date();

	@Column(name = "ENUNCIADO", length = 2000)
	private String enunciado;

	@Column(name = "REFERENCIA")
	private String referencia;

	@Column(name = "LINK_IMAGEM")
	private String linkImagem;

	@Column(name = "PONTUACAO")
	private double pontuacao;

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORIA_ID")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUB_CATEGORIA_ID")
	private SubCategoria subCategoria;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "QUESTAO__ALTERNATIVA", joinColumns = { @JoinColumn(name = "QUESTAO_ID") }, inverseJoinColumns = {
	@JoinColumn(name = "ALTERNATIVA_ID") })
	private List<Alternativa> alternativas;

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
	public void adicionarVariasAlternativas(List<Alternativa> alternativas) {
		this.alternativas.addAll(alternativas);
	}

	@Transient
	public void adicionarUmaAlternativa(Alternativa alternativa) throws Exception {
		if(alternativa != null && alternativaNaoAdicionada(alternativa)) {
			alternativa.setQuestao(this);
			this.alternativas.add(alternativa);
		}
		else
			throw new Exception("Objeto nulo ou Já adicionado");
	}
	
	@Transient
	public void editarUmaAlternativa(Alternativa alternativa) throws Exception {
		if(alternativa != null && alternativaNaoAdicionada(alternativa)) {
			int index = this.alternativas.indexOf(alternativa);
			this.alternativas.set(index, alternativa);
		}
		else
			throw new Exception("Objeto não encontrado");
	}
	
	@Transient
	public void removerUmaAlternativa(Alternativa alternativa) throws Exception {
		if(alternativa != null) {
			this.alternativas.remove(alternativa);
		}
		else
			throw new Exception("Objeto não encontrado");
	}
	
	@Transient
	public boolean alternativaJaAdicionada(Alternativa alternativa) throws Exception {
		if(alternativa != null) {
			return this.alternativas.stream().anyMatch(a -> a.equals(alternativa));
		}
		else
			throw new Exception("Objeto não encontrado");
	}
	
	@Transient
	public boolean alternativaNaoAdicionada(Alternativa alternativa) throws Exception {
		return !alternativaJaAdicionada(alternativa);
	}
	
	@Transient
	public boolean questaoTemAlternativaCorreta() throws Exception {
		 if(alternativas.stream().anyMatch(a->a.eCorreta())) 
			 return true;
		 else 
			 throw new Exception("Nas alternativas da questão deve constar uma questão correta");
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
		Questao other = (Questao) obj;
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
