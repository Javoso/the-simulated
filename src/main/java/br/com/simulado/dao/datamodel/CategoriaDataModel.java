package br.com.simulado.dao.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import br.com.simulado.dao.filter.Filter;
import br.com.simulado.models.Categoria;
import br.com.simulado.service.CategoriaService;

public class CategoriaDataModel extends org.primefaces.model.LazyDataModel<Categoria> implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient CategoriaService service;

	private Filter filter;

	public CategoriaDataModel(CategoriaService service, Filter filter) {
		this.service = service;
		this.filter = filter;
	}

	@Override
	public List<Categoria> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		setRowCount(service.count(filter).intValue());
		List<Categoria> sa = service.pesquisar(first, pageSize, filter);
		return sa;
	}

	@Override
	public Categoria getRowData(String referenciaLinha) {
		List<Categoria> categoria = getWrappedData();
		Long referencia = Long.valueOf(referenciaLinha);
		return categoria.stream().filter(c -> c.getId().equals(referencia)).findAny().orElse(null);
	}
}
