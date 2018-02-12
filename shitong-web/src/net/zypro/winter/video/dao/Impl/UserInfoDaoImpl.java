package net.zypro.winter.video.dao.Impl;



import java.util.List;






import org.hibernate.Query;
import org.hibernate.Session;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;
import net.zypro.winter.video.dao.UserInfoDao;

public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao {

	@Override
	public List<UserInfo> FindAllInfo(Session session) {
		return (List<UserInfo>)session.createQuery("select en from UserInfo en").list();
	}
//
//	@Override
//	public int addInfo(Session session, UserInfo userInfo) {       //�����Ϣ��������id
//		session.save(userInfo);      //���������ݿ��ˣ��ǳ�Ψһ
//		String hql="from UserInfo u where u.nick_name= :nick_name";     //ͨ��nick����������¼
//		Query query=session.createQuery(hql);
//		query.setParameter("nick_name", userInfo.getNick_name());
//		List<UserInfo> list=(List<UserInfo>)query.list();
//		if(list!=null){
//			return ((UserInfo)list.get(0)).getId();
//		}
//		return -1;
//	}

	@Override
	public List<UserInfo> FindByNickname(Session session, String nickname) {
		String hql="from UserInfo u where u.nick_name like:nick_name";
		Query query=session.createQuery(hql);
		query.setParameter("nick_name", "%"+nickname+"%");
		List<UserInfo> list=(List<UserInfo>)query.list();
		if(list.size()==0) return null;
		return list;
		
	}

	

}
