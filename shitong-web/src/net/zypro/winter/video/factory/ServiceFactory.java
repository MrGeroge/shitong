package net.zypro.winter.video.factory;

import org.hibernate.SessionFactory;

import net.zypro.winter.video.dao.Impl.AdminDaoImpl;
import net.zypro.winter.video.dao.Impl.CollectDaoImpl;
import net.zypro.winter.video.dao.Impl.CommentDaoImpl;
import net.zypro.winter.video.dao.Impl.FriendDaoImpl;
import net.zypro.winter.video.dao.Impl.LoveDaoImpl;
import net.zypro.winter.video.dao.Impl.MessageDaoImpl;
import net.zypro.winter.video.dao.Impl.RepublishDaoImpl;
import net.zypro.winter.video.dao.Impl.ShareDaoImpl;
import net.zypro.winter.video.dao.Impl.UserDaoImpl;
import net.zypro.winter.video.dao.Impl.UserInfoDaoImpl;
import net.zypro.winter.video.dao.Impl.VideoDaoImpl;
import net.zypro.winter.video.service.AdminManager;
import net.zypro.winter.video.service.FriendManager;
import net.zypro.winter.video.service.UserManager;
import net.zypro.winter.video.service.VideoManager;
import net.zypro.winter.video.util.HibernateUtil;


public class ServiceFactory {  //业务逻辑组件生产工厂
	public static AdminManager getAdminManager(){   //产生AdminManager组件
		AdminManager adminManager=new AdminManager();
		adminManager.setAdminDao(new AdminDaoImpl());
		adminManager.setSessionFactory(HibernateUtil.getSessionFactory());
		return adminManager;
	}
	public static UserManager getUserManager(){  //生产user业务逻辑组件
		UserManager userManager=new UserManager();
		userManager.setSessionFactory(HibernateUtil.getSessionFactory());
		userManager.setUserDao(new UserDaoImpl());
		userManager.setUserInfoDao(new UserInfoDaoImpl());
		return userManager;
	}
	public static VideoManager getVideoManager(){
		VideoManager videoManager=new VideoManager();
		videoManager.setLoveDao(new LoveDaoImpl());
		videoManager.setCollectDao(new CollectDaoImpl());
		videoManager.setCommentDao(new CommentDaoImpl());
		videoManager.setVideoDao(new VideoDaoImpl());
		videoManager.setRepublishDao(new RepublishDaoImpl());
		videoManager.setShareDao(new ShareDaoImpl());
		videoManager.setSessionFactory(HibernateUtil.getSessionFactory());
		return videoManager;
	}
	public static FriendManager getFriendManager(){
		SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
		FriendManager friendManager=new FriendManager();
		friendManager.setSessionFactory(sessionFactory);
		friendManager.setFriendDao(new FriendDaoImpl());
		friendManager.setUserDao(new UserDaoImpl());
		friendManager.setMessageDao(new MessageDaoImpl());
		friendManager.setUserInfoDao(new UserInfoDaoImpl());
		return friendManager;
	}
}
