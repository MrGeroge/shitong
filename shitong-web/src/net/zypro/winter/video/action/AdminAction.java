package net.zypro.winter.video.action;




import java.util.*;

import com.opensymphony.xwork2.ActionSupport;













import net.zypro.winter.video.bean.Admin;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.service.AdminManager;


import net.zypro.winter.video.service.VideoManager;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.json.JSONObject;

public class AdminAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Admin admin;
	private List<Video> videosNoStatusList;
	
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	@Action(value="/admin/login",results={
			@Result(name="success",location="/admin-video-passStatus-no.jsp",type="redirect"),
			@Result(name="error",location="/admin-login-error.jsp",type="redirect"),
			@Result(name="input",location="/admin-login.jsp",type="redirect"),
			@Result(name="invalid.token",location="/token-error.jsp")})
	//根据用户名与密码判断此人是否为管理员
	public String login(){
		AdminManager am=ServiceFactory.getAdminManager();
		int id=am.checkAdmin(admin);
		
		String fielderror="";int flag=0;
		if(admin.getUsername().trim().isEmpty()) {fielderror+="用户名不能为空"+" ";flag=1;}
		if(!admin.getUsername().trim().isEmpty()&&(admin.getUsername().trim().length()<5||admin.getUsername().trim().length()>12)) {fielderror+="输入的用户名长度必须在5到12位"+" ";flag=1;}
		if(admin.getPassword().trim().isEmpty()) {fielderror+="密码不能为空"+" ";flag=1;}
		if(!admin.getPassword().trim().isEmpty()&&(admin.getPassword().trim().length()<5||admin.getUsername().trim().length()>12)) {fielderror+="输入的密码长度必须在5到12位"+" ";flag=1;}
		if(flag==1){
			ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
			return "error";
		}
		
		if(id>0){
			admin.setId(id);
			ServletActionContext.getRequest().getSession().setAttribute("admin", admin);
			
			VideoManager vm=ServiceFactory.getVideoManager();
			videosNoStatusList=vm.findByStatus("no");
			if(videosNoStatusList!=null){
				ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
				}
			return SUCCESS;
		}
		else{
			fielderror+="用户名或密码不正确";
			ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
			return "error";
		}
	}
	
	
	
	
	
}
