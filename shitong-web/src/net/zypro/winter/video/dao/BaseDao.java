package net.zypro.winter.video.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

public interface BaseDao<T> {   //������dao�ӿڣ���װ��������ɾ��ķ���
	T Get(Session session,Class<T> clazz,Serializable id);  //ͨ��id��ѯ �����ʵ��
	Serializable Save(Session session,T entity) throws Exception;//save��entity����������֮��״̬Ϊpersist
	void Delete(Session session,T entity);//delete��entity��ɾ��ʵ����֮��״̬Ϊ͸��
	void Delete(Session session,Class<T> clazz,Serializable id);//��ͨ��idɾ������֮��״̬Ϊ͸��
	void Update(Session session,T entity);//update��entity������ʵ��������������״̬��
}