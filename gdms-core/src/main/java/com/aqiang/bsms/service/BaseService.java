package com.aqiang.bsms.service;

import java.util.List;

public interface BaseService<T> {
	public void saveEntitiy(T t);

	public void margeEntity(T t);

	public void deleteEntity(int id);

	public void batchEntityByJpql(String jpql, Object... objects);

	public T findEntity(Integer id);

	public List<T> findEntityByJpql(String jpql, Object... objects);

	public void deleteAll();

	public List<T> getAll();
}
