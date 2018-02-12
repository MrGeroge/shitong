package net.zypro.winter.video.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;
import net.zypro.winter.video.dao.UserDao;
import net.zypro.winter.video.dao.UserInfoDao;

public class UserManager {
private UserDao userDao;
private UserInfoDao userInfoDao;
private SessionFactory sessionFactory;
public UserDao getUserDao() {
	return userDao;
}
public void setUserDao(UserDao userDao) {
	this.userDao = userDao;
}
public UserInfoDao getUserInfoDao() {
	return userInfoDao;
}
public void setUserInfoDao(UserInfoDao userInfoDao) {
	this.userInfoDao = userInfoDao;
}
public SessionFactory getSessionFactory() {
	return sessionFactory;
}
public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}
public Map<String,Object> addUser(User user){//һ��url�ӿڶ�Ӧһ��ҵ���߼�������������(��Ӧregisterע���û�)
	Map<String,Object> map=new HashMap<>();
	String info="";
	Session session=null;
	User u=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		u=userDao.FindByUserName(session, user.getUsername());//�ж��û����Ƿ����
		if(u==null){
			info="�û�ע��ɹ�";      //���û�ע��ɹ�ʱ��Ҫ��ʼ��userInfo��Ϣ��������user��userInfo��
			map.put("info", info);
			map.put("status", true);
			UserInfo userInfo=new UserInfo();
			userInfo.setAddress("null");
			userInfo.setAge(12);
			userInfo.setAvatar_url("null");
			userInfo.setDesc("null");
			userInfo.setQq("null");
			userInfo.setSex("null");
			userInfo.setNick_name(user.getUsername());
			int id=(Integer) userInfoDao.Save(session, userInfo);       //����userInfo�������ݿ�  ���id
			userInfo.setId(id);
			user.setUserInfo(userInfo);
			userDao.Save(session, user);
			
		}
		else{
			info="�û����Ѿ�����";
			map.put("info", info);
			map.put("status", false);
		}
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
	return map;
}
public int checkUser(User user){//�ж��Ƿ���ڴ�user��¼����Ӧlogin�û���¼��
	Session session=null;
	User u=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		u=userDao.FindByUser(session, user);//�жϴ�user�Ƿ����(uΪ��)��������
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
	if(u!=null){
		return u.getId();
	}
	else
		return -1;
}
//�����û�ע�����õ���ҵ���߼��������˲���д��Ӧҵ��������ͬ���ϴ�ͷ��Ҳ����
public void updateUser(User user){   //��Ӧ���޸�����
	Session session=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		userDao.Update(session, user);//����id����²���
		session.getTransaction().commit();
	}
	catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
}
public int UpdateInfo(UserInfo userInfo){//�����û���Ϣ
	Session session=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		userInfoDao.Update(session, userInfo);
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
	return userInfo.getId();
}
public List<User> findAllUsers(){  //��Ӧ��detail
	Session session=null;
	List<User> users=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		users=userDao.FindAllUsers(session);
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
	return users;
	
}
public void updateActive(String username){
	Session session=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		userDao.updateActive(session, username);
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
}
public UserInfo findInfoByUser(User user){     //����user������Ϣ
	UserInfo userInfo=null;
	Session session=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		userInfo=userDao.FindInfoByUser(session, user);
		session.getTransaction().commit();
	}catch (Exception e) {
		if(session!=null)
		{
			if(session.getTransaction()!=null)
			{
				session.getTransaction().rollback();
			}
		}
		e.printStackTrace();
	}
	return userInfo;
}

}
