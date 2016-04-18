package com.aqiang.bsms.dao;

import java.util.List;

/**
 * BaseDao接口
 * 
 * @author aqiang
 * @param <T>
 */
public interface BaseDao<T> {

	public void saveEntitiy(T t);

	public void margeEntity(T t);

	public void deleteEntity(int id);

	public void batchEntityByJpql(String jpql, Object... objects);

	public T findEntity(Integer id);

	public List<T> findEntityByJpql(String jpql, Object... objects);

}
