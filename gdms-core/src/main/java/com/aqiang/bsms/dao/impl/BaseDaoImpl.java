package com.aqiang.bsms.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.aqiang.bsms.dao.BaseDao;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	Class<T> clazz;

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	@PersistenceContext
	EntityManager entityManager;

	public void saveEntitiy(T t) {
		entityManager.persist(t);
	}

	public void margeEntity(T t) {
		entityManager.merge(t);
	}

	public void deleteEntity(int id) {
		T t = this.findEntity(id);
		entityManager.remove(t);
	}

	public void batchEntityByJpql(String jpql, Object... objects) {
		Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i + 1, objects[i]);
		}
		query.executeUpdate();
	}

	public T findEntity(Integer id) {
		return entityManager.find(clazz, id);
	}

	public List<T> findEntityByJpql(String jpql, Object... objects) {
		Query query = entityManager.createQuery(jpql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i + 1, objects[i]);
		}
		return query.getResultList();
	}

}
