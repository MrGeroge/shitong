package net.zypro.winter.video.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface BaseDao<T> {   //基本的dao接口，封装基本的增删查改方法
	T Get(Session session,Class<T> clazz,Serializable id);  //通过id查询 ，获得实例
	Serializable Save(Session session,T entity) throws Exception;//save（entity）增操作，之后状态为persist
	void Delete(Session session,T entity);//delete（entity）删除实例，之后状态为透明
	void Delete(Session session,Class<T> clazz,Serializable id);//（通过id删除），之后状态为透明
	void Update(Session session,T entity);//update（entity）更新实例，必须在游离状态用
}