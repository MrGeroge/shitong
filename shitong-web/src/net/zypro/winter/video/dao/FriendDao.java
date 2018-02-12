package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

import net.zypro.winter.video.bean.Friend;
import net.zypro.winter.video.bean.User;

public interface FriendDao extends BaseDao<Friend>{

	public boolean ValidateFocus(Session session,User user,User fri);
	public Friend FindByUserAndFri(Session session,User user,User fri);
	public void DeleteByUserAndFri(Session session, User user, User fri);
	//获取所关注好友的User列表
	public List<Friend> GetFollowingListByUserId(Session session,int id);
	//获取粉丝的User列表
	public List<Friend> GetFollowersListByUserId(Session session,int id); 
}
