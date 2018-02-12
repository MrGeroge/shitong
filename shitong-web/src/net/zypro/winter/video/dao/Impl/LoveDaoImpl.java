package net.zypro.winter.video.dao.Impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.dao.LoveDao;

public class LoveDaoImpl extends BaseDaoImpl<Love> implements LoveDao {

	@Override
	public int FindLoveByVideoAndUser(Session session, User user, Video video) {
		List<Love> list=null;
		String hql="from Love l where l.user.id= :user_id and l.video.id= :video_id";
		Query query=session.createQuery(hql);
		query.setParameter("user_id", user.getId());
		query.setParameter("video_id", video.getId());
		list=(List<Love>)query.list();
		if(list.size()>0){//查找到匹配的记录
			return ((Love)list.get(0)).getId();
		}
		return -1;
	}

}
