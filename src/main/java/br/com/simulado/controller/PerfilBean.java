package br.com.simulado.controller;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import br.com.simulado.models.Usuario;
import br.com.simulado.security.Logado;
import br.com.simulado.service.UsuarioService;
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
	private List<String> conhecimentos = new ArrayList<>();

	@Getter
	@Setter
	private String nomeFile;

	@Override
	public void init() {
		conhecimentos = usuarioLogado.conhecimentos();
	}

	public boolean temImagem() {
		return isNotBlank(nomeFile);
	}

	public void handleFileUpload(FileUploadEvent event) {
		this.nomeFile = event.getFile().getFileName();
		this.usuarioLogado.setImagem(getEncodedContent(event.getFile().getContents()));
	}

	public String getEncodedContent(byte[] arquivo) {
		return DATA_BASE64.concat(Base64.getEncoder().encodeToString(arquivo));
	}

	public void atualizar() {
		if (nonNull(usuarioLogado)) {
			usuarioLogado.setConhecimentos(conhecimentos.stream().collect(Collectors.joining(", ")));
			service.merge(usuarioLogado);
			onSuccess("Informações atualizadas com sucesso!");
		}
	}
}