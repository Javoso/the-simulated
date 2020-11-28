package br.com.simulado.controller;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.dao.PermissaoDAO;
import br.com.simulado.models.Permissao;

@Named
@ViewScoped
public class PermissaoBean extends AbstractController {

	private static final long serialVersionUID = 6958517772569167273L;

	@Inject
	private PermissaoDAO permissaoDAO;
	
	private List<Permissao> permissoes;

	private Permissao permissao = new Permissao();

	@Override
	public void init() { }

	public void salvar() {
		try {
			nomePermissaoToUpperCase();
			permissaoDAO.save(permissao);
			onSuccess("Permissão salva com sucesso!");
			permissoes = null;
		} catch (Exception e) {
			onError("Erro ao tentar salvar a permissão!");
			logger.severe(e.getMessage());
		}
	}

	public void atualizar() {
		try {
			nomePermissaoToUpperCase();
			permissaoDAO.save(permissao);
			onSuccess("Permissão atualizada com sucesso!");
		} catch (Exception e) {
			onError("Erro ao tentar atualizar a permissão!");
			logger.severe(e.getMessage());
		}
	}

	public void clear() {
		this.permissao = new Permissao();
	}

	private void nomePermissaoToUpperCase() {
		this.permissao.setNome(this.permissao.getNome().toUpperCase().trim());
	}

	public Permissao getpermissao() {
		return permissao;
	}

	public void setpermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Permissao> getPermissoes() {
		if (permissoes == null)
			permissoes = permissaoDAO.findAll();

		return permissoes;
	}

}