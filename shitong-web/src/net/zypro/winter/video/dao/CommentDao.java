package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Comment;

public interface CommentDao extends BaseDao<Comment> { //������Ӷ�Comment���ݱ�Ĳ���
public List<Comment> findByVideoId(Session session,int id);
public List<Comment> FindAllComment(Session session);
}