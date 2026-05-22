package co.edu.unbosque.chistesneco.service;

import java.util.List;

public interface CRUDOperation<T> {
	
	public int create(T data);

	public int deleteById(Long id);

	public int updateById(Long id, T data);

	public List<T> getAll();

	public long count();

	public boolean exist(Long id);

}
