package net.zypro.winter.video.dao.Impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.Comment;
import net.zypro.winter.video.dao.CommentDao;

public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {

	public List<Comment> findByVideoId(Session session,int id) {
		String hql="from Comment c where c.video.id=:id";
		Query q=session.createQuery(hql);
		q.setParameter("id", id);
		List<Comment> list=(List<Comment>)q.list();
		return list;
	}

	@Override
	public List<Comment> FindAllComment(Session session) {
		return (List<Comment>)session.createQuery("select en from Comment en").list();
	}

}
