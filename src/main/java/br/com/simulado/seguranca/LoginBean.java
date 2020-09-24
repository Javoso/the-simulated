//package br.com.simulado.seguranca;
//
//
//import java.io.IOException;
//
//import javax.enterprise.inject.Model;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import br.com.simulado.controller.AbstractController;
//
//@Model
//public class LoginBean extends AbstractController{
//
//	private static final long serialVersionUID = -4669631840727056201L;
//
//	@Inject
//	private FacesContext facesContext;
//	
//	@Inject
//	private HttpServletRequest request;
//	
//	@Inject
//	private HttpServletResponse response;
//
//
//	public void login() throws ServletException, IOException {
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.xhtml");
//		dispatcher.forward(request, response);
//		
//		facesContext.responseComplete();
//	}
//
//	public void preRender() {
//		if ("true".equals(request.getParameter("invalid")))
//			onError("E-mail ou senha inválido");
//		
//	}
//
//	@Override
//	public void init() {
//	}
//}