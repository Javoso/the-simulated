package br.com.simulado.models.dto;

import java.io.Serializable;

import br.com.simulado.models.SimuladoRespondido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class SimuladoDTO implements Serializable {

	private static final long serialVersionUID = -1268040905259498175L;

	public SimuladoDTO(SimuladoRespondido simuladoRespondido) {
		this.idSimulado = simuladoRespondido.getSimulado().getId();
		this.nome = simuladoRespondido.getSimulado().getNome();
		this.idTentativa = simuladoRespondido.getTentativa().getId();
	}

	@Getter
	@Setter
	private Long idSimulado;

	@Getter
	@Setter
	private String nome;

	@Getter
	@Setter
	private Long idTentativa;

}
