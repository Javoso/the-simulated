package br.com.simulado.models;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.simulado.service.exception.NegocioException;
import br.com.simulado.util.AES;
import br.com.simulado.util.Criptografia;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USUARIO")
public class Usuario extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = 1140901365395325202L;

	@Include
	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "SENHA")
	private String senha;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DE_CRIACAO")
	private Date dataDeCriacao = new Date();

	@Type(type = "true_false")
	@Column(name = "STATUS")
	private Boolean status = true;
	
	@Column(name = "USU_IS_MUDAR_SENHA")
	@Type(type = "true_false")
	private boolean mudarSenha = true;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "USUARIO_PERMISSAO", joinColumns = { @JoinColumn(name = "USUARIO_ID") }, inverseJoinColumns = {
	@JoinColumn(name = "PERMISSAO_ID") })
	private List<Permissao> permissoes;

	public void adicionar(Permissao permissao) {
		if (isNull(permissao))
			throw new RuntimeException("A referência da permissão não pode ser nula!");
		this.getPermissoes().add(permissao);
	}

	public void atualizar(Permissao permissao) {
		if (isNull(permissao))
			throw new RuntimeException("A referência da permissão não pode ser nula!");
		int index = this.getPermissoes().indexOf(permissao);
		this.getPermissoes().set(index, permissao);
	}
	
	public void remover(Permissao permissao) {
		if (isNull(permissao))
			throw new RuntimeException("A referência da permissão não pode ser nula!");
		this.getPermissoes().remove(permissao);
	}

	public boolean temPermissao(String permissao) {
		return this.getPermissoes().stream().anyMatch(p -> p.getNome().equals(permissao));
	}

	public boolean temPermissao(Permissao permissao) {
		return this.getPermissoes().stream().anyMatch(p -> p.equals(permissao));
	}

	@Override
	public boolean isAtivo() {
		return Objects.equals(getStatus(), true);
	}

	@Override
	public boolean isNova() {
		return isNull(getId());
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
	public String getPaginaInicial() {
		return "/dashboard/dashboard.xhtml";
	}
	
	public void resetarSenha() throws NegocioException {
		if (senha == null)
			throw new NegocioException("a senha está nuloa");
		senha = new Criptografia().criptografar(senha);
	}

	public void resetarSenha(String senhaNova) {
		senha = new Criptografia().criptografar(senhaNova);
	}


	
}
