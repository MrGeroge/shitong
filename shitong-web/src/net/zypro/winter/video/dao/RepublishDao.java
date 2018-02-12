package net.zypro.winter.video.dao;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.Republish;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;

public interface RepublishDao extends BaseDao<Republish> {
public int FindRepublishByVideoAndUser(Session session,User user,Video video);
}
