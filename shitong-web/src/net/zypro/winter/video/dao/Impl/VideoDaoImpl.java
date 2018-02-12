package net.zypro.winter.video.dao.Impl;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.Republish;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.dao.VideoDao;

public class VideoDaoImpl extends BaseDaoImpl<Video> implements VideoDao {

	
	public void deleteById(Session session,int id) {
		String hql="delete from Video v where v.id=:id";
		Query q=session.createQuery(hql);
		q.setParameter("id", id);
		q.executeUpdate();
		
	}

	@Override
	public List<Video> findByKeyWord(Session session, String keyword,int beginIndex,int count) {//条件查询一般用hql
		String hql="from Video v where v.desc like :keyword order by v.time desc ";
		Query q=session.createQuery(hql);
		q.setParameter("keyword","%"+keyword+"%" );
		q.setFirstResult(beginIndex);
		q.setMaxResults(count);
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByTag(Session session, String tag,int beginIndex,int count) {
		String hql="from Video v where v.tag like :tag order by v.time desc";
		Query q=session.createQuery(hql);
		q.setParameter("tag", "%"+tag+"%");
		q.setFirstResult(beginIndex);
		q.setMaxResults(count);
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByUserName(Session session, String username,int beginIndex,int count) {
	String hql="from Video v where v.user.username=:username order by v.time desc";
		Query q=session.createQuery(hql);
		q.setParameter("username", username);
		q.setFirstResult(beginIndex);
		q.setMaxResults(count);
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByUserNameWithRepulished(Session session,
			String username,int beginIndex,int count) {
		List<Video> videos=new ArrayList<>();
		String hql="from Republish r where r.user.username= :username";
		Query q=session.createQuery(hql);
		q.setParameter("username", username);
		q.setFirstResult(beginIndex);
		q.setMaxResults(count);
		List<Republish> list=(List<Republish>)q.list();//查询一般用list获得所有该用户的转发记录
		for(int i=0;i<list.size();i++){
			Video video=list.get(i).getVideo();
			videos.add(video);
		}
		return videos;
	}

	@Override
	public List<Video> findByRecommend(Session session) {
    return null;
	}

	@Override
	public List<Video> FindAllVideo(Session session) {
		return (List<Video>)session.createQuery("select en from Video en order by en.time desc").list();
	}
	public List<Video> FindAllVideo(Session session,int beginIndex,int count){
		String hql="select en from Video en order by en.time desc";
		Query q=session.createQuery(hql);
		q.setFirstResult(beginIndex);
		q.setMaxResults(count);
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByKeyWord(Session session, String keyword) {
		String hql="from Video v where v.desc like :keyword order by v.time desc ";
		Query q=session.createQuery(hql);
		q.setParameter("keyword","%"+keyword+"%" );
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByTag(Session session, String tag) {
		String hql="from Video v where v.tag like :tag order by v.time desc";
		Query q=session.createQuery(hql);
		q.setParameter("tag", "%"+tag+"%");
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByUserName(Session session, String username) {
		String hql="from Video v where v.user.username=:username order by v.time desc";
		Query q=session.createQuery(hql);
		q.setParameter("username", username);
		List<Video> list=(List<Video>)q.list();//查询一般用list
		return list;
	}

	@Override
	public List<Video> findByUserNameWithRepulished(Session session,
			String username) {
		List<Video> videos=new ArrayList<>();
		String hql="from Republish r where r.user.username= :username";
		Query q=session.createQuery(hql);
		q.setParameter("username", username);
		List<Republish> list=(List<Republish>)q.list();//查询一般用list获得所有该用户的转发记录
		for(int i=0;i<list.size();i++){
			Video video=list.get(i).getVideo();
			videos.add(video);
		}
		return videos;

	}

	@Override
	public List<Video> FindByStatus(Session session,String status) {
		String hql="from Video v where v.status=:status";
		Query q=session.createQuery(hql);
		q.setParameter("status", status);
		List<Video> list=(List<Video>)q.list();
		return list;
	}

	@Override
	public int FindViewNumberByVideo(Session session, Video video) {//查找某个video的viewNumber
		List<Video> list=null;
		String hql="from Video v where v.id= :id";
		Query q=session.createQuery(hql);
		q.setParameter("id", video.getId());
		list=(List<Video>)q.list();
		if(list!=null){
			return ((Video)list.get(0)).getViewNumber();
		}
		else
			return -1;    //查找失败
	}

	@Override
	public int FindLovedNumberByVideo(Session session, Video video) {
		List<Video> list=null;
		String hql="from Video v where v.id= :id";
		Query q=session.createQuery(hql);
		q.setParameter("id", video.getId());
		list=(List<Video>)q.list();
		if(list!=null){
			return ((Video)list.get(0)).getLovedNumber();
		}
		else
			return -1;    //查找失败
	}

	@Override
	public int FindRepublishedNumberByVideo(Session session, Video video) {
		List<Video> list=null;
		String hql="from Video v where v.id= :id";
		Query q=session.createQuery(hql);
		q.setParameter("id", video.getId());
		list=(List<Video>)q.list();
		if(list!=null){
			return ((Video)list.get(0)).getRepublishedNumber();
		}
		else
			return -1;    //查找失败
	}

	@Override
	public int FindSharedNumberByVideo(Session session, Video video) {
		List<Video> list=null;
		String hql="from Video v where v.id= :id";
		Query q=session.createQuery(hql);
		q.setParameter("id", video.getId());
		list=(List<Video>)q.list();
		if(list!=null){
			return ((Video)list.get(0)).getSharedNumber();
		}
		else
			return -1;    //查找失败
	}

	@Override
	public void addViewNumberByVideo(Session session, Video video) {
		int viewNumber=FindViewNumberByVideo(session,video);
		if(viewNumber>=0){
		viewNumber=viewNumber+1;
		String hql="update Video v set v.viewNumber= :viewNumber where v.id= :id";
		Query q=session.createQuery(hql);
		q.setParameter("viewNumber", viewNumber);
		q.setParameter("id", video.getId());
		q.executeUpdate();
		}
	}

	@Override
	public void addLovedNumberByVideo(Session session, Video video) {
		int lovedNumber=FindLovedNumberByVideo(session, video);
		if(lovedNumber>=0){
			lovedNumber=lovedNumber+1;
			String hql="update Video v set v.lovedNumber= :lovedNumber where v.id= :id";
			Query q=session.createQuery(hql);
			q.setParameter("lovedNumber", lovedNumber);
			q.setParameter("id", video.getId());
			q.executeUpdate();
		}
		
	}

	@Override
	public void addRepublishedNumberByVideo(Session session, Video video) {
		int republishedNumber=FindRepublishedNumberByVideo(session, video);
		if(republishedNumber>=0){
			republishedNumber=republishedNumber+1;
			String hql="update Video v set v.republishedNumber= :republishedNumber where v.id= :id";
			Query q=session.createQuery(hql);
			q.setParameter("republishedNumber", republishedNumber);
			q.setParameter("id", video.getId());
			q.executeUpdate();
		}
		
		
	}

	@Override
	public void addSharedNumberByVideo(Session session, Video video) {
		int sharedNumber=FindSharedNumberByVideo(session, video);
		if(sharedNumber>=0){
			sharedNumber=sharedNumber+1;
			String hql="update Video v set v.sharedNumber= :sharedNumber where v.id= :id";
			Query q=session.createQuery(hql);
			q.setParameter("sharedNumber", sharedNumber);
			q.setParameter("id", video.getId());
			q.executeUpdate();
		}
		
		
	}

}
