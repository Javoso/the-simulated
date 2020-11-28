package br.com.simulado.dao.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Simulado;
import br.com.simulado.service.SimuladoService;

public class SimuladoDataModel extends org.primefaces.model.LazyDataModel<Simulado> implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient SimuladoService service;

	private Filter filter;

	public SimuladoDataModel(SimuladoService service, Filter filter) {
		this.service = service;
		this.filter = filter;
	}

	@Override
	public List<Simulado> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		setRowCount(service.count(filter).intValue());
		List<Simulado> sa = service.pesquisar(first, pageSize, filter);
		return sa;
	}

	@Override
	public Simulado getRowData(String referenciaLinha) {
		List<Simulado> simulado = getWrappedData();
		Long referencia = Long.valueOf(referenciaLinha);
		return simulado.stream().filter(c -> c.getId().equals(referencia)).findAny().orElse(null);
	}
}
