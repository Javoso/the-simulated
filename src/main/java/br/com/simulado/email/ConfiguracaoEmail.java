package br.com.simulado.email;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

@Entity
@Cacheable
@Table(name = "CONFIGURACAO_EMAIL")
public class ConfiguracaoEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String hostname;// = "smtp.gmail.com";
	private Integer port;// = 587;
	private boolean ssl;// = true;
	private String username;
	private String password;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMA_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "EMA_HOSTNAME", length = 50, nullable = false)
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Column(name = "EMA_PORTA", nullable = false)
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "EMA_SSL")
	@Type(type = "true_false")
	public boolean getSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	@Email(message = "E-mail inválido!")
	@Column(name = "EMA_USUARIO", length = 100, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "EMA_SENHA", length = 200, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public boolean isNova() {
		return getId() == null;
	}

	@Transient
	public boolean isCadastrada() {
		return !isNova();
	}

}