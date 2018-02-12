package net.zypro.winter.video.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.zypro.winter.video.bean.Friend;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.dao.FriendDao;
import net.zypro.winter.video.dao.UserDao;
import net.zypro.winter.video.util.HibernateUtil;

public class FriendDaoImpl extends BaseDaoImpl<Friend> implements FriendDao{

	@Override
	public boolean ValidateFocus(Session session, User user, User fri) {
		if(user.getId()==fri.getId()) return true;
		
		String hql="FROM Friend A WHERE A.userId=:id1 AND A.friUserId=:id2";
		Query query=session.createQuery(hql);
		
		query.setParameter("id1", user.getId());
		query.setParameter("id2",fri.getId());
		
		List results=query.list();
		
		if(results.size()==0) return false;
		return true;
	}
    
	@Override
	public void DeleteByUserAndFri(Session session, User user, User fri) {
		Friend friend=FindByUserAndFri(session, user, fri);
		session.delete(friend);
	}

	@Override
	public Friend FindByUserAndFri(Session session, User user, User fri) {
		String hql="FROM Friend A WHERE A.userId=:id1 AND A.friUserId=:id2";
		Query query=session.createQuery(hql);
		
		query.setParameter("id1", user.getId());
		query.setParameter("id2", fri.getId());
		
		List result=query.list();
		
		if(result.size()==0) return null;
		return (Friend)result.get(0);
	}

	@Override
	//获取所关注好友的User列表
	public List<Friend> GetFollowingListByUserId(Session session,int id) {
		String hql="FROM Friend A WHERE A.userId=:id";
		Query query=session.createQuery(hql);
		
		query.setParameter("id", id);
		
		List result=query.list();
		
		if(result.size()==0) return null;
		return (List<Friend>)result;
	}

	@Override
	//获取粉丝的User列表
	public List<Friend> GetFollowersListByUserId(Session session ,int id) {
		String hql="FROM Friend A WHERE A.friUserId=:id";
		Query query=session.createQuery(hql);
		
		query.setParameter("id", id);
		
		List result=query.list();
		
		if(result.size()==0) return null;
		return (List<Friend>)result;
	}
}
