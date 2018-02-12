package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;


public interface UserDao extends BaseDao<User> {  //允许添加对User数据表的操作
public User FindByUserId(Session session,int id);
public User FindByUserName(Session session,String username);
public List<User> FindAllUsers(Session session);
public List<User> FindListByUserName(Session session,String name);
public User findByUserInfo(Session session,UserInfo userInfo);
public User FindByUser(Session session,User user);
public void updateActive(Session session,String username); //修改该用户的活跃性
public UserInfo FindInfoByUser(Session session,User user);                                                         //根据用户名找到userInfo对象
}
