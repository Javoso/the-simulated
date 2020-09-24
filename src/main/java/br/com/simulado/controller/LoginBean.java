package br.com.simulado.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

@Model
public class LoginBean extends AbstractController {

	private static final long serialVersionUID = 747341059329169105L;

	@Inject
	private FacesContext context;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;
	
	@Getter
	@Setter
	private boolean aceiteOsTermos = false;

	@Override
	public void init() { }

	private static final String URL_LOGIN = "/login.xhtml";

	public void acessar() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(URL_LOGIN);
		dispatcher.forward(request, response);
		context.responseComplete();
	}

	public void preRender() {
		if ("true".equals(request.getParameter("invalid"))) {
			onError("Erro! Matrícula ou senha inválidos!");
		}

	}

	public String anoAtual() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(new Date());
	}

}