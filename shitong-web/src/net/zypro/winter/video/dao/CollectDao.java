package net.zypro.winter.video.dao;

import java.util.List;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Collect;

public interface CollectDao extends BaseDao<Collect> {//允许添加对Collect数据表的操作
public List<Collect> FindAllCollect(Session session);
}