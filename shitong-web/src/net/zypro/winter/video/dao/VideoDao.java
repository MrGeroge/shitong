package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Video;

public interface VideoDao extends BaseDao<Video> {//������Ӷ�Video���ݱ�Ĳ���
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
public int FindViewNumberByVideo(Session session,Video video); //���ĳ����Ƶ�Ĺۿ�����
public int FindLovedNumberByVideo(Session session,Video video);  //���ĳ����Ƶ��ϲ������
public int FindRepublishedNumberByVideo(Session session,Video video);//��ȡĳ����Ƶ��ת������
public int FindSharedNumberByVideo(Session session,Video video);  //��ȡ�������
public void addViewNumberByVideo(Session session,Video video);
public void addLovedNumberByVideo(Session session,Video video);
public void addRepublishedNumberByVideo(Session session,Video video);
public void addSharedNumberByVideo(Session session,Video video);
} 