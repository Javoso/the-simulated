package br.com.simulado.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.simulado.models.Tentativa;
import br.com.simulado.models.Usuario;
import br.com.simulado.models.dto.SimuladoDTO;
import br.com.simulado.security.Logado;
import br.com.simulado.service.SimuladoRespondidoService;
import br.com.simulado.service.TentativaService;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class DashboardController extends AbstractController {

	private static final long serialVersionUID = 8770610991326531611L;

	@Inject
	@Logado
	private Usuario estudanteLogado;

	@Inject
	private TentativaService tentativaService;

	@Inject
	private SimuladoRespondidoService simuladoRespondidoService;

	@Getter
	@Setter
	private SimuladoDTO simuladoDTO = new SimuladoDTO();

	@Getter
	@Setter
	private List<SimuladoDTO> simuladosDTO = new ArrayList<>();

	@Getter
	@Setter
	private List<Tentativa> tentativas = new ArrayList<>();

	@Override
	public void init() {
		simuladosDTO = simuladoRespondidoService.simuladosPorEstudante(estudanteLogado);
	}

	public boolean isListTentativasIsNotEmpty() {
		return !tentativas.isEmpty();
	}

	public void pesquisar() {
		try {
			tentativas = tentativaService.findBySimulado(simuladoDTO.getIdSimulado());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
