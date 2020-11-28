package br.com.simulado.controller;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.simulado.email.ConfiguracaoEmail;
import br.com.simulado.service.ConfiguracaoEmailService;
import br.com.simulado.util.AES;

@Named
@ViewScoped
public class ConfiguracaoEmailBean extends AbstractController {

	private static final long serialVersionUID = 131489939315118542L;

	@Inject
	private ConfiguracaoEmailService service;

	private ConfiguracaoEmail configuracao;
	private Boolean mudarSenha = false;
	private String senha;

	@Override
	public void init() {
		configuracao = service.getConfiguracao();

		if (configuracao == null)
			configuracao = new ConfiguracaoEmail();

	}

	public void save() {
		try {
			if (StringUtils.isNotBlank(senha))
				configuracao.setPassword(new AES().codificar(senha));

			service.saveOrUpdate(configuracao);
			onSuccess("Configuração salva com sucesso!");

		} catch (Exception e) {
			onError("Erro ao salvar a configuração!");
			logger.severe(e.getMessage());
		}
	}

	public void update() {
		try {
			if (StringUtils.isNotBlank(senha))
				configuracao.setPassword(new AES().codificar(senha));

			service.saveOrUpdate(configuracao);
			onError("Configuração atualizada com sucesso!");

		} catch (Exception e) {
			onError("Erro ao atualizada a configuração!");
			logger.severe(e.getMessage());
		}
	}

	public boolean isNova() {
		return configuracao.isNova();
	}

	public boolean isCadastrada() {
		return configuracao.isCadastrada();
	}

	public ConfiguracaoEmail getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoEmail configuracao) {
		this.configuracao = configuracao;
	}

	public Boolean getMudarSenha() {
		return mudarSenha;
	}

	public void setMudarSenha(Boolean mudarSenha) {
		this.mudarSenha = mudarSenha;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}