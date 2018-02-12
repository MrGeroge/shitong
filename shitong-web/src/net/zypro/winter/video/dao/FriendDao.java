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
	//��ȡ����ע���ѵ�User�б�
	public List<Friend> GetFollowingListByUserId(Session session,int id);
	//��ȡ��˿��User�б�
	public List<Friend> GetFollowersListByUserId(Session session,int id); 
}
