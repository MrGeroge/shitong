package net.zypro.winter.video.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import net.zypro.winter.video.util.HibernateUtil;
import junit.framework.TestCase;

public class UserDaoImplTest extends TestCase {

	public void testUpdateActive() {    //updateActive³É¹¦
		SessionFactory sf=HibernateUtil.getSessionFactory();
		Session session=sf.getCurrentSession();
		UserDaoImpl udi=new UserDaoImpl();
		session.beginTransaction();
		udi.updateActive(session, "caiqi");
		session.getTransaction().commit();
	}

}
