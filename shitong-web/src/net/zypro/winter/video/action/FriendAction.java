package net.zypro.winter.video.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;


import org.json.*;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserWithNameAvatarNick;
import net.zypro.winter.video.bean.UserWithNameAvatarNickTag;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.service.FriendManager;
import net.zypro.winter.video.service.VideoManager;

public class FriendAction {
	private User user;
	private User fri;
	private JSONObject jsonObject=new JSONObject();
	
	private int videoId;


	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	
	public User getFri() {
		return fri;
	}

	public void setFri(User fri) {
		this.fri = fri;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Action(value="/friend/add")
	public void AddFriend() throws IOException{
		//����user��id��username
		HttpServletRequest request=ServletActionContext.getRequest();
	    HttpSession session=request.getSession();
		user=(User)session.getAttribute("user");
		
		if(user!=null&&user.getId()>0&&fri.getUsername().trim()!=""&&fri!=null){
			FriendManager friendManager=ServiceFactory.getFriendManager();
			
			System.out.println(fri.getUsername());
			
			Map<String, Object> result=friendManager.AddFriend(user, fri.getUsername());
			jsonObject.put("message", result.get("message"));
			jsonObject.put("status", result.get("status"));
			jsonObject.put("tag", result.get("tag"));
			ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
		    return ;
		}
		jsonObject.put("message", "�����쳣");
	    jsonObject.put("status", "error");
	    jsonObject.put("tag", "failed");
	    ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
	    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
	    return;
		
	}
	@Action(value="/friend/find")
	public void FindFriend() throws IOException {
		//��ȡsession�е��û�user
		HttpServletRequest request=ServletActionContext.getRequest();
	    HttpSession session=request.getSession();
		user=(User)session.getAttribute("user");
		
		if(user!=null&&user.getId()>0&&fri.getUsername().trim()!=""&&fri!=null){ 
			FriendManager friendManager=ServiceFactory.getFriendManager();
			List<UserWithNameAvatarNickTag> list=friendManager.FindFriendList(fri.getUsername());
			if(list.isEmpty()){
				JSONArray jsonArray=new JSONArray();
				jsonObject.put("status", "success");
			    jsonObject.put("message", "û����֮ƥ�����");
			    jsonObject.put("arr",jsonArray);
			    ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
			    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
			    return;
			}
			else{
				JSONArray jsonArray=new JSONArray(list);
				jsonObject.put("status", "success");
				jsonObject.put("message", "���ҳɹ�");
				jsonObject.put("arr",jsonArray);
			    ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
			    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
			    return;
			}
			
		}
		JSONArray jsonArray=new JSONArray();
		jsonObject.put("status", "error");
	    jsonObject.put("message", "�����쳣");
	    jsonObject.put("arr",jsonArray);
	    ServletActionContext.getResponse().setContentType("text/html,charset=GBK");
	    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
	    return;
	}
	@Action(value="/friend/delete")
	public void DeleteFriend() throws IOException {
		HttpServletRequest request=ServletActionContext.getRequest();
	    HttpSession session=request.getSession();
		user=(User)session.getAttribute("user");
		
		if(user!=null&&user.getId()>0&&fri.getUsername().trim()!=""&&fri!=null){
		    FriendManager friendManager=ServiceFactory.getFriendManager();
		    Map<String, String> params=friendManager.deleteFriend(user, fri.getUsername());
		    jsonObject.put("message", params.get("message"));
		    jsonObject.put("status", params.get("status"));
		    jsonObject.put("tag", params.get("tag"));
		    ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
		    return;
		}
		    jsonObject.put("message", "�����쳣");
		    jsonObject.put("status", "error");
		    jsonObject.put("tag", "failed");
		    ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		    ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
		    return;
    }
	//��ȡ��˿�б�
	@Action(value="/friend/getFollowers")
	public void getFollowersList() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		
		FriendManager friendManager=ServiceFactory.getFriendManager();
		List<UserWithNameAvatarNickTag> list=friendManager.getFollowersListByUserId1(user.getId());
		JSONArray jsonArray=new JSONArray(list);
		jsonObject.put("status", "success");
		jsonObject.put("arr", jsonArray);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
	}
	//��ȡ��ע���б�
    @Action(value="/friend/getFollowing")
    public void getFollowingList() throws IOException{
		HttpServletRequest request=ServletActionContext.getRequest();
		User user=(User)request.getSession().getAttribute("user");
		
		FriendManager friendManager=ServiceFactory.getFriendManager();
		List<UserWithNameAvatarNickTag> list=friendManager.getFollowingListByUserId1(user.getId());
		JSONArray jsonArray=new JSONArray(list);
		
		jsonObject.put("status", "success");
		jsonObject.put("arr", jsonArray);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonObject.toString());
	}
    
   
}
