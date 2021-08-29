package br.com.simulado.models;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario extends EntidadeGenerica<Long> implements Serializable {

	private static final long serialVersionUID = 1140901365395325202L;

	@Include
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "EMAIL", unique = true)
	private String email;

	@Lob
	@Column(name = "IMAGEM")
	private String imagem;

	@Column(name = "FORMACAO", length = 5000)
	private String formacao;

	@Column(name = "CONHECIMENTOS")
	private String conhecimentos;

	@Column(name = "CIDADE")
	private String cidade;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "RUA")
	private String rua;

	@Column(name = "BAIRRO")
	private String bairro;

	@Column(name = "NUMERO")
	private String numero;

	@Column(name = "DESCRICAO", length = 5000)
	private String descricao;

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
	private boolean mudarSenha = false;

	@ManyToMany
	@JoinTable(name = "USUARIO_PERMISSAO", joinColumns = { @JoinColumn(name = "USUARIO_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PERMISSAO_ID") })
	private List<Permissao> permissoes = new ArrayList<>();

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
			throw new NegocioException("a senha está nulo");
		senha = new Criptografia().criptografar(senha);
	}

	public void resetarSenha(String senhaNova) {
		senha = new Criptografia().criptografar(senhaNova);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", dataDeCriacao="
				+ dataDeCriacao + ", status=" + status + ", mudarSenha=" + mudarSenha + ", permissoes=" + permissoes
				+ "]";
	}

	@Transient
	public List<String> conhecimentos() {
		if (isNotBlank(getConhecimentos()))
			return Arrays.asList(getConhecimentos().split(","));
		else
			return Arrays.asList();
	}

	@Transient
	public List<String> adicionarConhecimentos(List<String> conhecimentos) {
		return Arrays.asList(getConhecimentos().split(","));
	}

	@Transient
	public boolean temImagem() {
		return isNotBlank(imagem);
	}

	@Transient
	public boolean temFormacao() {
		return isNotBlank(formacao);
	}

	@Transient
	public boolean temConhecimento() {
		return isNotBlank(conhecimentos);
	}

	@Transient
	public boolean temDescricao() {
		return isNotBlank(descricao);
	}

	@Transient
	public boolean temEndereco() {
		return isNotBlank(estado) && isNotBlank(cidade) && isNotBlank(rua) && isNotBlank(numero);
	}

	@Transient
	public boolean temInformacoes() {
		return temConhecimento() && temDescricao() && temEndereco() && temFormacao();
	}

	@Transient
	public String getNomeUsuarioLogado() {
		String nome = "";
		try {
			nome = getNome();
			return nome.substring(0, nome.indexOf(' '));
		} catch (StringIndexOutOfBoundsException e) {
			return nome;
		} catch (NullPointerException e) {
			return nome;
		}
	}
}