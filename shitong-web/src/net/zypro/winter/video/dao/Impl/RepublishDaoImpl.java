package net.zypro.winter.video.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.Republish;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.dao.RepublishDao;

public class RepublishDaoImpl extends BaseDaoImpl<Republish> implements RepublishDao {

	@Override
	public int FindRepublishByVideoAndUser(Session session, User user,
			Video video) {
		List<Republish> list=null;
		String hql="from Republish r where r.user.id= :user_id and r.video.id= :video_id";
		Query query=session.createQuery(hql);
		query.setParameter("user_id", user.getId());
		query.setParameter("video_id", video.getId());
		list=(List<Republish>)query.list();
		if(list.size()>0){//查找到匹配的记录
			return ((Republish)list.get(0)).getId();
		}
		return -1;
	}
	}


