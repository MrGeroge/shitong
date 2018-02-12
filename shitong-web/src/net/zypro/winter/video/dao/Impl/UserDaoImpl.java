package net.zypro.winter.video.dao.Impl;

import java.util.List;

import javax.persistence.criteria.From;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;
import net.zypro.winter.video.dao.UserDao;


public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    public User FindByUserId(Session session,int id){
    	String hql="from User u where u.id= :id";
    	Query q=session.createQuery(hql);
    	q.setParameter("id", id);
    	List<User> list=q.list();
        if(list.size()==0){
        	return null; 
        }
        else 
        	return (User)list.get(0);
    }
	public User FindByUserName(Session session, String username) {  //通过用户名获得user记录
    String hql=" from User u where u.username like :username";
    Query query = session.createQuery(hql);
	query.setParameter("username","%"+username+"%");
	List results = query.list();
	
	if(results.size()==0)
	{
		return null;
	}
	
	return (User)results.get(0);
	}
	@Override
	public List<User> FindAllUsers(Session session) {
		return (List<User>)session.createQuery("select en from User en").list();
	}	
	
	@Override
	public List<User> FindListByUserName(Session session, String name) {
		String hql="FROM User A WHERE A.username like:name";
		Query query=session.createQuery(hql);
		query.setParameter("name", "%"+name+"%");
		List result=query.list();
		if(result.size()==0) return null;
		return (List<User>)result;
		
	}
	@Override
	public User FindByUser(Session session, User user) {
		 
		String hql="from User u where u.username= :username and u.password= :password";
		 Query query = session.createQuery(hql);
		 query.setParameter("username", user.getUsername());
		 query.setParameter("password", user.getPassword());
		 List<User> list=query.list();
		 if(list.size()==0){
			 return null;
		 }
		 else
			 return (User)list.get(0);
	}
	@Override
	public void updateActive(Session session, String username) {
		String hql="update User u set u.isActive=1 where u.username= :username";
		Query query=session.createQuery(hql);
		query.setParameter("username", username);
		query.executeUpdate();
	}
	@Override
	public UserInfo FindInfoByUser(Session session, User user) {
		String hql="from User u where u.username= :username";
		Query query=session.createQuery(hql);
		query.setParameter("username", user.getUsername());
		List<User> list=(List<User>)query.list();
		if(list!=null){
			return ((User)list.get(0)).getUserInfo();
		}
		else 
			return null;
		
	}
	@Override
	public User findByUserInfo(Session session,UserInfo userInfo) {
		String hql="from User u where u.userInfo=:userInfo";
		Query query=session.createQuery(hql);
		query.setParameter("userInfo",userInfo);
		List result=query.list();
		if(result.size()==0) return null;
		return (User)result.get(0);
	}
}
