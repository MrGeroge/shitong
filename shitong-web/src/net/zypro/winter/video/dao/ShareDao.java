package net.zypro.winter.video.dao;

import org.hibernate.Session;

import net.zypro.winter.video.bean.Share;
import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;

public interface ShareDao extends BaseDao<Share> {
	public int FindShareByVideoAndUser(Session session,User user,Video video);  //查找某用户对某视频分享记录
}
