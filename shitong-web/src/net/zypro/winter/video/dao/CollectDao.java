package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Collect;

public interface CollectDao extends BaseDao<Collect> {//������Ӷ�Collect���ݱ�Ĳ���
public List<Collect> FindAllCollect(Session session);
}