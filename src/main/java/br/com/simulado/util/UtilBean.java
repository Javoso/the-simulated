package br.com.simulado.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class UtilBean implements Serializable {

	private static final long serialVersionUID = -9016054290587687586L;

	private boolean fundamentosComputacao = false;
	private boolean matematica = false;
	private boolean tecnologiasComputacao = false;

	private String link = "https://www.youtube-nocookie.com/embed/70ng8cn4St0?controls=0";

	@Inject
	private HttpServletRequest request;

	@PostConstruct
	public void init() {
		if ("true".equals(request.getParameter("fundamento"))) {
			fundamentos();
		}
		if ("true".equals(request.getParameter("matematica"))) {
			matematica();
		}
		if ("true".equals(request.getParameter("tecnologia"))) {
			tecnologias();
		}
	}

	public void fundamentos() {
		fundamentosComputacao = true;
	}

	public void matematica() {
		matematica = true;
	}

	public void tecnologias() {
		tecnologiasComputacao = true;
	}

	public List<Long> getValues() {
		List<Long> a = new ArrayList<>();
		
		for (Long i = 0L; i < 20L; i++) {
			a.add(i);
		}
		
		return a;
	}

	public Matematica[] getMatematicaValues() {
		return Matematica.values();
	}

	public FundamentosComputacao[] getFundamentosComputacaoValues() {
		return FundamentosComputacao.values();
	}

	public TecnologiasComputacao[] getTecnologiasComputacaoValues() {
		return TecnologiasComputacao.values();
	}

	public boolean isFundamentosComputacao() {
		return fundamentosComputacao;
	}

	public void setFundamentosComputacao(boolean fundamentosComputacao) {
		this.fundamentosComputacao = fundamentosComputacao;
	}

	public boolean isMatematica() {
		return matematica;
	}

	public void setMatematica(boolean matematica) {
		this.matematica = matematica;
	}

	public boolean isTecnologiasComputacao() {
		return tecnologiasComputacao;
	}

	public void setTecnologiasComputacao(boolean tecnologiasComputacao) {
		this.tecnologiasComputacao = tecnologiasComputacao;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
