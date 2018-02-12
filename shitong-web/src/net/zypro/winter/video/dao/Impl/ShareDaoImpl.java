package net.zypro.winter.video.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.Share;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.dao.ShareDao;

public class ShareDaoImpl extends BaseDaoImpl<Share> implements ShareDao {

	@Override
	public int FindShareByVideoAndUser(Session session, User user, Video video) {
		List<Share> list=null;
		String hql="from Share s where s.user.id= :user_id and s.video.id= :video_id";
		Query query=session.createQuery(hql);
		query.setParameter("user_id", user.getId());
		query.setParameter("video_id", video.getId());
		list=(List<Share>)query.list();
		if(list.size()>0){//查找到匹配的记录
			return ((Share)list.get(0)).getId();
		}
		return -1;
	}
	}


