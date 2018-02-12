package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Admin;

public interface AdminDao extends BaseDao<Admin> {   //������Ӷ�Admin���ݱ�Ĳ���
public Admin FindByAdminId(Session session,int id);
public List<Admin> FindAllAdmin(Session session);
public Admin FindByAdminNameAndPwd(Session session,String username,String password);
public Admin FindByAdminName(Session session,String username);
}