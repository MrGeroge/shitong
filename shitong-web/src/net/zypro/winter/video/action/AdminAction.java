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
	//�����û����������жϴ����Ƿ�Ϊ����Ա
	public String login(){
		AdminManager am=ServiceFactory.getAdminManager();
		int id=am.checkAdmin(admin);
		
		String fielderror="";int flag=0;
		if(admin.getUsername().trim().isEmpty()) {fielderror+="�û�������Ϊ��"+" ";flag=1;}
		if(!admin.getUsername().trim().isEmpty()&&(admin.getUsername().trim().length()<5||admin.getUsername().trim().length()>12)) {fielderror+="������û������ȱ�����5��12λ"+" ";flag=1;}
		if(admin.getPassword().trim().isEmpty()) {fielderror+="���벻��Ϊ��"+" ";flag=1;}
		if(!admin.getPassword().trim().isEmpty()&&(admin.getPassword().trim().length()<5||admin.getUsername().trim().length()>12)) {fielderror+="��������볤�ȱ�����5��12λ"+" ";flag=1;}
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
			fielderror+="�û��������벻��ȷ";
			ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
			return "error";
		}
	}
	
	
	
	
	
}
