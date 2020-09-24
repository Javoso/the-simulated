package br.com.simulado.dao;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.formula.functions.T;

import br.com.simulado.dao.filter.Filter;


public interface DAO<T> {
	
	public void save(T t);
	
	public void mudarStatus(T t);

	public T merge(T t);
	
	public T findById(Long id);
	
	public Long count(Filter filter);
	
	public List<T> pesquisar(int primeiro, int ultimo, Filter filter);
	
	public List<T> pesquisar(Filter filter);
	
	public List<T> todas();
	
	public Predicate[] restricoes(Filter filter, Root<T> root);
	
	
	
}
