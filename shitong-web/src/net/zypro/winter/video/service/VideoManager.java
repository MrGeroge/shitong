package net.zypro.winter.video.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;










import net.zypro.winter.video.bean.Comment;
import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.Republish;
import net.zypro.winter.video.bean.Share;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.dao.CollectDao;
import net.zypro.winter.video.dao.CommentDao;
import net.zypro.winter.video.dao.LoveDao;
import net.zypro.winter.video.dao.RepublishDao;
import net.zypro.winter.video.dao.ShareDao;
import net.zypro.winter.video.dao.VideoDao;
import net.zypro.winter.video.util.HibernateUtil;
import net.zypro.winter.video.util.PageUtil;

public class VideoManager {//封装对于video的业务操作
	private VideoDao videoDao;
	private CollectDao collectDao;
	private CommentDao commentDao;
	private LoveDao loveDao;
	private RepublishDao republishDao;
	private ShareDao shareDao;
	private SessionFactory sessionFactory;
	public VideoDao getVideoDao() {
		return videoDao;
	}
	public void setVideoDao(VideoDao videoDao) {
		this.videoDao = videoDao;
	}
	public CollectDao getCollectDao() {
		return collectDao;
	}
	public void setCollectDao(CollectDao collectDao) {
		this.collectDao = collectDao;
	}
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public LoveDao getLoveDao() {
		return loveDao;
	}
	public void setLoveDao(LoveDao loveDao) {
		this.loveDao = loveDao;
	}
	
	public RepublishDao getRepublishDao() {
		return republishDao;
	}
	public void setRepublishDao(RepublishDao republishDao) {
		this.republishDao = republishDao;
	}
	
	public ShareDao getShareDao() {
		return shareDao;
	}
	public void setShareDao(ShareDao shareDao) {
		this.shareDao = shareDao;
	}
	public void addVideo(Video video){   //对应与/video/edit
		Session session=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			videoDao.Save(session, video);
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
		}		
	}
	public List<Video> findAllVideos(PageUtil page){    //对应于/video/list
		List<Video> videos=null;
		Session session=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			page.setTotalCount(videoDao.FindAllVideo(session).size());
			videos=videoDao.FindAllVideo(session,page.getBeginIndex(), page.getEveryPage());
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
		}
		return videos;
	}
	public List<Comment> findCommentsByVideoId(int id){//对应与/video/list
		Session session=null;
		List<Comment> list=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			list=commentDao.findByVideoId(session, id);
			session.getTransaction().commit();		
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;
	}
	public  void deleteByVideoId(int id){//对应于/video/delete
		Session session=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			videoDao.deleteById(session, id);
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
	}
	public List<Video> findBykeyWord(PageUtil page,String keyword){
		Session session=null;
		List<Video> list=new ArrayList<>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			page.setTotalCount(videoDao.findByKeyWord(session, keyword).size());
			list=videoDao.findByKeyWord(session,keyword,page.getBeginIndex(), page.getEveryPage());
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;
	}
	public List<Video> findByTag(PageUtil page,String tag){
		Session session=null;
		List<Video> list=new ArrayList<>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			page.setTotalCount(videoDao.findByTag(session, tag).size());
			list=videoDao.findByTag(session, tag,page.getBeginIndex(), page.getEveryPage());
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;
	}
	public List<Video> findByUserName(PageUtil page,String username){
		Session session=null;
		List<Video> list=new ArrayList<>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			page.setTotalCount(videoDao.findByUserName(session, username).size());
			list=videoDao.findByUserName(session, username,page.getBeginIndex(), page.getEveryPage());
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;
	}
	public List<Video> findByUserNameWithRepulished(PageUtil page,String username){
		Session session=null;
		List<Video> list=new ArrayList<>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			page.setTotalCount(videoDao.findByUserNameWithRepulished(session, username).size());
			list=videoDao.findByUserNameWithRepulished(session, username,page.getBeginIndex(), page.getEveryPage());
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;	
	}
	public List<Video> findByRecommend(){
		Session session=null;
		List<Video> list=new ArrayList<>();
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			list=videoDao.findByRecommend(session);
			session.getTransaction().commit();
		}catch (Exception e) {
			if(session!=null)
			{
				if(session.getTransaction()!=null)
				{
					session.getTransaction().rollback();
				}
			}
			e.printStackTrace();
	}
		return list;	
	}
	public void passStatus(int videoId){
		Session session=null;
		Video video=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			video=videoDao.Get(session, Video.class, videoId);
			video.setStatus("yes");
			videoDao.Update(session, video);
			
			session.getTransaction().commit();
			
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}
	public void cancelStatus(int videoId){
		Session session=null;
		Video video=null;
		try{
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			
			video=videoDao.Get(session, Video.class, videoId);
			video.setStatus("no");
			videoDao.Update(session, video);
			
			session.getTransaction().commit();
			
		}catch(Exception e){
			if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
		}
	}
	//返回未审核的视频
     public List<Video>  findByStatus(String status){
    	 Session session=null;
    	 List<Video> list=new ArrayList<>();
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 
    		 list=videoDao.FindByStatus(session,status);
    		 
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 }
    	 return list;
     }
     public Video findByVideoId(int videoId){
    	 Session session=null;
    	 Video video=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 
    		 video=videoDao.Get(session, Video.class, videoId);
    		 
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 }
    	 return video;
     }
     
     public void updateVideo(Video video){
    	 Session session=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 
    		 videoDao.Update(session, video);
    		 
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 }
     }
     public void addViewNumber(Video video){   //增加某个视频观看次数
    	 Session session=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 videoDao.addViewNumberByVideo(session, video);   //数据库中数据+1
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 }
    	 
     }
     public void addLovedNumber(Video video){
    	 Session session=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 videoDao.addLovedNumberByVideo(session, video);   //数据库中数据+1
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 } 
     }
     public void addRepublishedNumber(Video video){
    	 Session session=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 videoDao.addRepublishedNumberByVideo(session, video);   //数据库中数据+1
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 } 
     }
     public void addSharedNumber(Video video){
    	 Session session=null;
    	 try{
    		 session=sessionFactory.getCurrentSession();
    		 session.beginTransaction();
    		 videoDao.addSharedNumberByVideo(session, video);  //数据库中数据+1
    		 session.getTransaction().commit();
    	 }catch(Exception e){
    		 if(session!=null){
 				if(session.getTransaction()!=null)
 					session.getTransaction().rollback();
 			}
 			e.printStackTrace();
    	 } 
     }
    public boolean addLove(User user,Video video){   //视频点赞的处理逻辑
    	Session session=null;
    	boolean tag=false;   //点赞是否成功
    	Love love=null;
    	try{
    		session=sessionFactory.getCurrentSession();
    		session.beginTransaction();
    		int id=loveDao.FindLoveByVideoAndUser(session, user, video);  //查看是否存在某用户点赞某视频的记录
    		if(id>0){    //之前已经点赞过了
    		
    		}
    		else{    //之前该用户未对此视频点赞过
    		love=new Love();
    		love.setUser(user);
    		love.setVideo(video);
    		loveDao.Save(session, love);
    		videoDao.addLovedNumberByVideo(session, video);  //lovedNumber+1
    		tag=true;
    		}
    		session.getTransaction().commit();
    	}catch(Exception e){
   		 if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
   	 } 
    	return tag;
    }
    public boolean addRepublish(User user,Video video){   //视频点赞的处理逻辑
    	Session session=null;
    	boolean tag=false;   //点赞是否成功
    	Republish republish=null;
    	try{
    		session=sessionFactory.getCurrentSession();
    		session.beginTransaction();
    		int id=republishDao.FindRepublishByVideoAndUser(session, user, video);
    		if(id>0){    //之前已经转发过了
    		
    		}
    		else{    //之前该用户未对此视频转发过
    		republish=new Republish();
    		republish.setUser(user);
    		republish.setVideo(video);
    		republishDao.Save(session, republish);
    		videoDao.addRepublishedNumberByVideo(session, video);  //republishNumber+1
    		tag=true;
    		}
    		session.getTransaction().commit();
    	}catch(Exception e){
   		 if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
   	 } 
    	return tag;
    }
    public boolean addShare(User user,Video video){   //视频分享的处理逻辑
    	Session session=null;
    	boolean tag=false;   //分享是否成功
    	Share share=null;
    	try{
    		session=sessionFactory.getCurrentSession();
    		session.beginTransaction();
    		int id=shareDao.FindShareByVideoAndUser(session, user, video);  //查看是否存在某用户分享某视频的记录
    		if(id>0){    //之前已经分享过了
    		
    		}
    		else{    //之前该用户未对此视频分享过
    		share=new Share();
    		share.setUser(user);
    		share.setVideo(video);
    		shareDao.Save(session, share);
    		videoDao.addSharedNumberByVideo(session, video);  //sharedNumber+1
    		tag=true;
    		}
    		session.getTransaction().commit();
    	}catch(Exception e){
   		 if(session!=null){
				if(session.getTransaction()!=null)
					session.getTransaction().rollback();
			}
			e.printStackTrace();
   	 } 
    	return tag;
    } 
}
