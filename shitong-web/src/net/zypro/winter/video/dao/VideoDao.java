package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Video;

public interface VideoDao extends BaseDao<Video> {//允许添加对Video数据表的操作
public void deleteById(Session session,int id);
public List<Video> findByKeyWord(Session session,String keyword,int beginIndex,int count);
public List<Video> findByKeyWord(Session session,String keyword);
public List<Video> findByTag(Session session,String tag,int beginIndex,int count);
public List<Video> findByTag(Session session,String tag);
public List<Video> findByUserName(Session session,String username,int beginIndex,int count);
public List<Video> findByUserName(Session session,String username);
public List<Video> findByUserNameWithRepulished(Session session,String username,int beginIndex,int count);
public List<Video> findByUserNameWithRepulished(Session session,String username);
public List<Video> findByRecommend(Session session);
public List<Video> FindAllVideo(Session session);
public List<Video> FindAllVideo(Session session,int beginIndex,int count);
public List<Video> FindByStatus(Session session,String status);
public int FindViewNumberByVideo(Session session,Video video); //获得某个视频的观看次数
public int FindLovedNumberByVideo(Session session,Video video);  //获得某个视频的喜爱次数
public int FindRepublishedNumberByVideo(Session session,Video video);//获取某个视频的转发次数
public int FindSharedNumberByVideo(Session session,Video video);  //获取分享次数
public void addViewNumberByVideo(Session session,Video video);
public void addLovedNumberByVideo(Session session,Video video);
public void addRepublishedNumberByVideo(Session session,Video video);
public void addSharedNumberByVideo(Session session,Video video);
} 