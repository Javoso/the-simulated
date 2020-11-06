package br.com.simulado.dao;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.simulado.dao.filter.Filter;


public interface DAO<E> {
	
	public void save(E e);
	
	public void mudarStatus(E e);

	public E merge(E e);
	
	public E findById(Long id);
	
	public Long count(Filter filter);
	
	public List<E> pesquisar(int primeiro, int ultimo, Filter filter);
	
	public List<E> pesquisar(Filter filter);
	
	public List<E> todas();
	
	public Predicate[] restricoes(Filter filter, Root<E> root);
	

}
