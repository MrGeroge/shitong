package net.zypro.winter.video.dao.Impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.hql.internal.ast.HqlASTFactory;

import net.zypro.winter.video.bean.Admin;
import net.zypro.winter.video.dao.AdminDao;

public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao {

	@Override
	public Admin FindByAdminId(Session session, int id) {//通过admin_id在数据库中查找admin记录
		Admin admin=(Admin)session.get(Admin.class, id);
		return admin;
	}

	@Override
	public List<Admin> FindAllAdmin(Session session) {
		return (List<Admin>)session.createQuery("select en from Admin en").list();
	}

	@Override
	public Admin FindByAdminNameAndPwd(Session session, String username,
			String password) {
		// TODO Auto-generated method stub
		String hql="FROM Admin A WHERE A.username=:username AND A.password=:password";
		Query query=session.createQuery(hql);
		
		query.setParameter("username", username);
		query.setParameter("password",password);
		
		List results=query.list();
		
		if(results.size()==0) return null;
		return (Admin)results.get(0);
	}

	@Override
	public Admin FindByAdminName(Session session, String username) {
		String hql="FROM Admin A WHERE A.username=:username";
		Query query=session.createQuery(hql);
		
		query.setParameter("username", username);
		
		List results=query.list();
		
		if(results.size()==0) return null;
		return (Admin)results.get(0);
	}


	
}
