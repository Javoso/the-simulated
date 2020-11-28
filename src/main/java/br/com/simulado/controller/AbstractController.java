package br.com.simulado.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.simulado.infra.jsf.FacesUtil;
import br.com.simulado.infra.jsf.Messages;
import br.com.simulado.util.AES;

@SuppressWarnings("serial")
public abstract class AbstractController implements Serializable {

	@Inject
	private Messages helper;

	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	protected transient Logger logger;

	@PostConstruct
	public void postConstruct() {
		init();
	}

	public abstract void init();
	
	protected boolean isPostback() {
		return facesUtil.isPostBack();
	}
	protected boolean isNotPostback() {
		return facesUtil.isNotPostBack();
	}

	protected void onWarn(String message) {
		helper.addWarnMessage(message);
	}
	
	protected void onSuccess(String message) {
		helper.addInfoMessage(message);
	}

	protected void onSuccessWithFlash(String message) {
		helper.addFlash(message);
	}

	protected void onError(String message) {
		helper.addErrorMessage(message);
	}

	protected void onError(String message, Throwable e) {
		onError(message);
	}

	protected void onFailed(String message) {
		helper.validationFailed(message);
	}

	protected void updateComponents(String... components) {
		facesUtil.updateComponents(false, components);
	}

	protected void executeJS(String javascript) {
		facesUtil.executeJS(javascript);
	}

	protected String getParamName(String paramName) {
		return facesUtil.getParamName(paramName);
	}

	protected String getParamNameDecodificado(String paramName) {
		
		try {
			return new AES().decodificar(getParamName(paramName));
		} catch (NullPointerException e) {
			logger.severe("Nenhum parâmetro encontrado");
		}
		return null;
	}
	
	protected String getParamNameCodificado(Long paramName) {
		
		try {
			return new AES().codificar(paramName.toString());
		} catch (NullPointerException e) {
			logger.severe("Nenhum parâmetro informado");
		}
		return null;
	}
	
	protected void redirect(String url) {
		facesUtil.redirect(url);
	}

}
