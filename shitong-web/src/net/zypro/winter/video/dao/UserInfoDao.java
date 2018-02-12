package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo> {//允许添加对UserInfo数据表的操作
public List<UserInfo> FindAllInfo(Session session);
//public int addInfo(Session session,UserInfo userInfo);
public List<UserInfo> FindByNickname(Session session,String nickname);
}