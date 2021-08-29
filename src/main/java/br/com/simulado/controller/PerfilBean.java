package br.com.simulado.controller;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.com.simulado.models.Usuario;
import br.com.simulado.security.Logado;
import br.com.simulado.service.UsuarioService;
import br.com.simulado.util.AES;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class PerfilBean extends AbstractController {

	private static final long serialVersionUID = -1896677787891133585L;

	private static final String DATA_BASE64 = "data:image/png;base64,";

	@Inject
	private UsuarioService service;

	@Getter
	@Setter
	@Inject
	@Logado
	private Usuario usuarioLogado;

	@Getter
	@Setter
	private String base64;

	@Getter
	@Setter
	private List<String> conhecimentos = Collections.emptyList();

	@Getter
	@Setter
	private String nomeFile = "";

	private byte[] arquivo;

	@Override
	public void init() {
		try {
			if (!usuarioLogado.conhecimentos().isEmpty()) {
				conhecimentos = usuarioLogado.conhecimentos();
				nomeFile = "";
			}
		} catch (NullPointerException e) {

		}
	}

	public boolean temImagem() {
		return isNotBlank(nomeFile);
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (nonNull(event)) {
			this.nomeFile = event.getFile().getFileName();
			arquivo = event.getFile().getContents();
			onSuccessWithFlash("Imagem enviada com sucesso!");
		}
	}

	public String getEncodedContent(byte[] arquivo) {
		return DATA_BASE64.concat(Base64.getEncoder().encodeToString(arquivo));
	}

	public String atualizar() {
		if (nonNull(usuarioLogado) && nonNull(arquivo)) {
			this.usuarioLogado.setImagem(getEncodedContent(arquivo));
			try {
				if (nonNull(conhecimentos))
					usuarioLogado.setConhecimentos(conhecimentos.stream().collect(Collectors.joining(", ")));
			} catch (NullPointerException e) {

			}
			service.merge(usuarioLogado);
			onSuccessWithFlash("Informações atualizadas com sucesso!");
			return "/perfil/perfil.xhtml?faces-redirect=true";
		}
		return "";
	}
}