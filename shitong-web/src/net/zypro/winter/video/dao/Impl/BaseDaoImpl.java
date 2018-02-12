package net.zypro.winter.video.dao.Impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;




import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {

	@Override
	public T Get(Session session, Class<T> clazz, Serializable id) {
		T entity=(T)session.get(clazz, id);
		return entity;
	}

	@Override
	public Serializable Save(Session session, T entity) throws Exception {
		session.save(entity);
		Method getId=entity.getClass().getMethod("getId");
		return (Serializable)getId.invoke(entity);
		
	}

	@Override
	public void Delete(Session session, T entity) {
		session.delete(entity);
	}

	@Override
	public void Delete(Session session, Class<T> clazz, Serializable id) {
		T t=(T)session.get(clazz, id);
		session.delete(t);
		
	}

	@Override
	public void Update(Session session, T entity) {
		session.update(entity);   //根据实例是否有id来判断
	}


}
