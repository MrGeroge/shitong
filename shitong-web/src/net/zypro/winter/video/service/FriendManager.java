package net.zypro.winter.video.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.zypro.winter.video.bean.Friend;
import net.zypro.winter.video.bean.Message;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;
import net.zypro.winter.video.bean.UserWithNameAvatarNick;
import net.zypro.winter.video.bean.UserWithNameAvatarNickTag;
import net.zypro.winter.video.dao.FriendDao;
import net.zypro.winter.video.dao.MessageDao;
import net.zypro.winter.video.dao.UserDao;
import net.zypro.winter.video.dao.UserInfoDao;
import net.zypro.winter.video.dao.Impl.MessageDaoImpl;
import net.zypro.winter.video.dao.Impl.UserDaoImpl;

public class FriendManager{
	private FriendDao friendDao;
	private MessageDao messageDao;
	private UserDao userDao;
	private UserInfoDao userInfoDao;
	private SessionFactory sessionFactory;
	
	
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Map<String, Object> AddFriend(User user,User fri){
		Session session=null;
		
		Map<String, Object> params=new HashMap<String, Object>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			//userA已经关注了friB
		    if(friendDao.ValidateFocus(session, user, fri)){
		    	params.put("message", "已关注");
		    	params.put("status", "failed");
		    	params.put("tag", "failed");
		    }//userA没有关注friB
		    else{
		    	Friend friend=new Friend();
		    	friend.setUserId(user.getId());
		    	friend.setFriUserId(fri.getId());
		    	
		    	friendDao.Save(session, friend);
		    	
		    	
		    	
		    	params.put("message", "关注好友成功");
		    	params.put("status", "success");
		    	if(friendDao.ValidateFocus(session, fri, user))
		    		params.put("tag", "相互关注");
		    	else 
		    		params.put("tag", "已关注");
		    }
		    
			session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null){
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
		}
		return params;
	}
		
	public Map<String, Object> 	 AddFriend(User user,String friName){
		Map<String,Object> params=new HashMap<String, Object>();
		User fri=new User();
		Session session=null;
		
		try{
	    session=sessionFactory.getCurrentSession();
	    session.beginTransaction();
			
	    fri=userDao.FindByUserName(session, friName);
		if(fri==null){
			params.put("message", "没有与之匹配的人");
			params.put("status", "nobody");
			params.put("tag", "failed");
			session.getTransaction().commit();
			return params;
		}
		
		   session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
		}
		
		params=AddFriend(user, fri);
		return params;
	}
	
	
	 //根据用户名或昵称查找用户列表
    public List<UserWithNameAvatarNickTag> FindFriendList(String username){
    	
       List<UserWithNameAvatarNickTag> results=new ArrayList<UserWithNameAvatarNickTag>();
	   Session session=null;
	   try{
		   session=sessionFactory.getCurrentSession();
		   session.beginTransaction();
		   
		   HashSet<User> users=new HashSet<>();
		   //根据用户名搜索的用户列表
		   List<User> usersByUsername=userDao.FindListByUserName(session, username);
		   for(User u:usersByUsername){
			   users.add(u);
		   }
		   //根据昵称搜索的用户列表
		   List<UserInfo> userInfos=userInfoDao.FindByNickname(session, username);
		   if(userInfos!=null){
			   for(UserInfo userInfo:userInfos){
				   User u=userDao.findByUserInfo(session, userInfo);
				   users.add(u);
			   }
		   }
		   //整合昵称，用户名搜索的用户列表
		   List<User> userList=new ArrayList<User>(users);
		   
		   User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		   
		   
		   if(userList!=null){
			   for(User u:userList){
				   //好友列表中删除用户本身
				   if(u.getUsername().equals(user.getUsername())) continue;
				   
				   String avatar=u.getUserInfo().getAvatar_url();
				   String nickname=u.getUserInfo().getNick_name();
				   String tag="";
				   if(friendDao.ValidateFocus(session, user, u)){
					   if(friendDao.ValidateFocus(session, u, user))
						   tag="相互关注";
					   else
						   tag="已关注";
				   }else {
					tag="未关注";
				 }
				   if(user.getId()==u.getId()) {
					   tag="相互关注";
				   }
				   UserWithNameAvatarNickTag result=new UserWithNameAvatarNickTag(u.getUsername(), nickname, avatar,tag);
				   results.add(result);
			   }
		   }
		   
		   session.getTransaction().commit();
	   }catch(Exception e){
		   if(session!=null){
			   if(session.getTransaction()!=null)
				   session.getTransaction().rollback();
		   }
	   }
	   
	   return results;
   }
    
	public Map<String, String> deleteFriend(User user,User fri){
		Session session=null;
		Map<String, String> params=new HashMap<String, String>();
		
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			if(!friendDao.ValidateFocus(session, user, fri)||user.getId()==fri.getId()){
				params.put("message", "不是好友关系");
				params.put("status", "failed");
				params.put("tag", "failed");
			}else{
				friendDao.DeleteByUserAndFri(session, user, fri);
				params.put("message", "取消关注好友成功");
				params.put("status", "success");
				params.put("tag", "未关注");
			}
			
			session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
		}
		return params;
	}
	public Map<String, String> deleteFriend(User user,String friName){
		Map<String, String> params=new HashMap<String, String>();
		User fri=new User();
		Session session=null;
		
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();

			fri=userDao.FindByUserName(session, friName);
		    if(fri==null){ 
		    	params.put("message", "没有与之匹配的人");
				params.put("status", "nobody");
				params.put("tag", "failed");
				session.getTransaction().commit();
				return params;
		    }
               session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
		}
		
		params=deleteFriend(user, fri);
		return params;
		
	}
	
	
	
	//获取所关注好友的User列表
	public List<User> getFollowingListByUserId(int id){
		Session session=null;
		List<User> list=new ArrayList<User>();
		List<Friend> friends=new ArrayList<Friend>();
		
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			friends=friendDao.GetFollowingListByUserId(session, id);
			
			if(friends==null){ 
				session.getTransaction().commit();
			    return new ArrayList<User>();
			}
			
			for(Friend friend:friends){
				User user=userDao.Get(session, User.class, friend.getFriUserId());
				list.add(user);
			}
			session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
		}
		
		return list;
	}
	//获取所关注好友的UserWithNameAvatarNick列表
	public List<UserWithNameAvatarNickTag> getFollowingListByUserId1(int id){
		   List<UserWithNameAvatarNickTag> results=new ArrayList<UserWithNameAvatarNickTag>();
		   List<User> list=getFollowingListByUserId(id);
		   Session session=null;
		   
		   User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		   
		   try{
				session=sessionFactory.getCurrentSession();
				session.beginTransaction();
				
		   for(User u:list){
			   String avatar=u.getUserInfo().getAvatar_url();
			   String nickname=u.getUserInfo().getNick_name();
			   String tag="";
			   if(friendDao.ValidateFocus(session, user, u)){
				   if(friendDao.ValidateFocus(session, u, user))
					   tag="相互关注";
				   else
					   tag="已关注";
			 }
			   if(user.getId()==u.getId()) {
				   tag="相互关注";
			   }
			   UserWithNameAvatarNickTag result=new UserWithNameAvatarNickTag(u.getUsername(), nickname, avatar,tag);
		       results.add(result);
		   }
		   
		   session.getTransaction().commit();
		   }catch(Exception e){
			   if(session!=null){
				   if(session.getTransaction()!=null)
					   session.getTransaction().rollback();
			   }
		   }
		   
		   return results;
	}
	
	//获取粉丝的User列表
    public List<User> getFollowersListByUserId(int id){
    	Session session=null;
		List<User> list=new ArrayList<User>();
		List<Friend> fans=new ArrayList<Friend>();
		
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			fans=friendDao.GetFollowersListByUserId(session, id);
			
			if(fans==null){ 
				session.getTransaction().commit();
			    return new ArrayList<User>();
			}
			
			for(Friend friend:fans){
				User user=userDao.Get(session, User.class, friend.getUserId());
				list.add(user);
			}
			session.getTransaction().commit();
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
		}
		
		return list;
    }
    //获取粉丝的UserWithNameAvatarNick列表
    public List<UserWithNameAvatarNickTag> getFollowersListByUserId1(int id){
    List<UserWithNameAvatarNickTag> results=new ArrayList<UserWithNameAvatarNickTag>();
	   List<User> list=getFollowersListByUserId(id);
	   Session session=null;
	   
	   User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
	   
	   try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
	   
	   for(User u:list){
		   String avatar=u.getUserInfo().getAvatar_url();
		   String nickname=u.getUserInfo().getNick_name();
		   
		   String tag="";
		   if(friendDao.ValidateFocus(session, user, u)){
			   if(friendDao.ValidateFocus(session, u, user))
				   tag="相互关注";
			   else
				   tag="已关注";
		   }else {
			tag="未关注";
		 }
		   if(user.getId()==u.getId()) {
			   tag="相互关注";
		   }
		   UserWithNameAvatarNickTag result=new UserWithNameAvatarNickTag(u.getUsername(), nickname, avatar,tag);
		   results.add(result);
	   }

			session.getTransaction().commit();
		} catch (Exception e) {
			if (session != null) {
				if (session.getTransaction() != null)
					session.getTransaction().rollback();
			}
		}
		   
	   return results;
    }
}
