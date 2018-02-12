package net.zypro.winter.video.dao.Impl;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Collect;
import net.zypro.winter.video.dao.CollectDao;

public class CollectDaoImpl extends BaseDaoImpl<Collect> implements CollectDao {

	@Override
	public List<Collect> FindAllCollect(Session session) {
		return (List<Collect>)session.createQuery("select en from Collect en").list();
	}

}
