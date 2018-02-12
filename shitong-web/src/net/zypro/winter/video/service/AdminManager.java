package net.zypro.winter.video.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.zypro.winter.video.bean.Admin;
import net.zypro.winter.video.dao.AdminDao;

public class AdminManager {
private AdminDao adminDao;
private SessionFactory sessionFactory;

public AdminDao getAdminDao() {
	return adminDao;
}

public void setAdminDao(AdminDao adminDao) {
	this.adminDao = adminDao;
}

public SessionFactory getSessionFactory() {
	return sessionFactory;
}

public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}
public int checkAdmin(Admin admin){//判断admin是否存在
	Admin admin1=null;
	Session session=null;
	try{
	session=sessionFactory.getCurrentSession();
	session.beginTransaction();
	admin1=adminDao.FindByAdminNameAndPwd(session, admin.getUsername(), admin.getPassword());
	
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
	if(admin1!=null){
		return admin1.getId();   //存在此admin
	}
	else 
		return -1;
}
public void updateAdmin(Admin admin){
	Session session=null;
	try{
	session=sessionFactory.getCurrentSession();
	session.beginTransaction();
	adminDao.Update(session, admin);
	
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
public int validateName(String username){
	Session session=null;
	Admin admin=null;
	try{
	session=sessionFactory.getCurrentSession();
	session.beginTransaction();
	admin=adminDao.FindByAdminName(session, username);
	
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
	if(admin!=null) return 0;
	return 1;
}
}
