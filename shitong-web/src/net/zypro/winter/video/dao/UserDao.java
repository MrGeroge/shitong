package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;


public interface UserDao extends BaseDao<User> {  //������Ӷ�User���ݱ�Ĳ���
public User FindByUserId(Session session,int id);
public User FindByUserName(Session session,String username);
public List<User> FindAllUsers(Session session);
public List<User> FindListByUserName(Session session,String name);
public User findByUserInfo(Session session,UserInfo userInfo);
public User FindByUser(Session session,User user);
public void updateActive(Session session,String username); //�޸ĸ��û��Ļ�Ծ��
public UserInfo FindInfoByUser(Session session,User user);                                                         //�����û����ҵ�userInfo����
}
