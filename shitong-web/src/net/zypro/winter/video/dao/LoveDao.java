package net.zypro.winter.video.dao;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Love;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;

public interface LoveDao extends BaseDao<Love> {
public int FindLoveByVideoAndUser(Session session,User user,Video video);  //����ĳ�û���ĳ��Ƶ���޼�¼
}
