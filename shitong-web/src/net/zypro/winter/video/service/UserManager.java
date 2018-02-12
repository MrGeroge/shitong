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
public Map<String,Object> addUser(User user){//一个url接口对应一个业务逻辑操作（方法）(对应register注册用户)
	Map<String,Object> map=new HashMap<>();
	String info="";
	Session session=null;
	User u=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		u=userDao.FindByUserName(session, user.getUsername());//判断用户名是否存在
		if(u==null){
			info="用户注册成功";      //当用户注册成功时需要初始化userInfo信息，建立此user与userInfo关
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
			int id=(Integer) userInfoDao.Save(session, userInfo);       //将此userInfo存入数据库  获得id
			userInfo.setId(id);
			user.setUserInfo(userInfo);
			userDao.Save(session, user);
			
		}
		else{
			info="用户名已经存在";
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
public int checkUser(User user){//判断是否存在此user记录（对应login用户登录）
	Session session=null;
	User u=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		u=userDao.FindByUser(session, user);//判断此user是否存在(u为空)，有问题
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
//由于用户注销不用调用业务逻辑组件，因此不必写对应业务处理方法，同理上传头像也不用
public void updateUser(User user){   //对应于修改密码
	Session session=null;
	try{
		session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		userDao.Update(session, user);//存在id则更新操作
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
public int UpdateInfo(UserInfo userInfo){//更新用户信息
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
public List<User> findAllUsers(){  //对应于detail
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
public UserInfo findInfoByUser(User user){     //根据user查找信息
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
