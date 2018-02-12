package net.zypro.winter.video.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONObject;

import net.zypro.winter.video.bean.Admin;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.service.AdminManager;
import net.zypro.winter.video.service.VideoManager;

public class AdminManagerAction {
private Admin admin;
private int videoId;
private String to;
private String newUsername;
private String newPassword1;
private String newPassword2;

private String videoDesc;

public String getVideoDesc() {
	return videoDesc;
}

public void setVideoDesc(String videoDesc) {
	this.videoDesc = videoDesc;
}

public String getNewUsername() {
	return newUsername;
}

public void setNewUsername(String newUsername) {
	this.newUsername = newUsername;
}

public String getTo() {
	return to;
}

public void setTo(String to) {
	this.to = to;
}

public Admin getAdmin() {
	return admin;
}

public void setAdmin(Admin admin) {
	this.admin = admin;
}

public String getNewPassword1() {
	return newPassword1;
}

public void setNewPassword1(String newPassword1) {
	this.newPassword1 = newPassword1;
}

public String getNewPassword2() {
	return newPassword2;
}

public void setNewPassword2(String newPassword2) {
	this.newPassword2 = newPassword2;
}


public int getVideoId() {
	return videoId;
}

public void setVideoId(int videoId) {
	this.videoId = videoId;
}

@Action(value="/adminVideo/delete",results={
        @Result(name="passStatus-no",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="passStatus-yes",location="/admin-video-passStatus-yes.jsp",type="redirect"),
        @Result(name="error",location="/admin-login.jsp")
})
public String deleteByVideoId() throws IOException{
	if(videoId>0){
		
	VideoManager vm=ServiceFactory.getVideoManager();
	vm.deleteByVideoId(videoId);
	List<Video> videosNoStatusList;
	if(to.equals("passStatus-yes")){
	     videosNoStatusList=vm.findByStatus("yes");
	}
	else{
		 videosNoStatusList=vm.findByStatus("no");
	}
	if(videosNoStatusList!=null){
		ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
	}
	if(to.equals("passStatus-yes")) return "passStatus-yes";
	else return "passStatus-no";
	
	}else
		return "error";
	
}
@Action(value="/adminVideo/passStatus",
results={@Result(name="success",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="error",location="/admin-login.jsp")
}
)
public String passStatus() throws IOException{
	if(videoId>0){
	
	VideoManager vm=ServiceFactory.getVideoManager();
	vm.passStatus(videoId);
	List<Video> videosNoStatusList=vm.findByStatus("no");
	if(videosNoStatusList!=null){
		ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
		}
	return "success";
    }else
    	return "error";
}
@Action(value="/adminVideo/cancelStatus",
results={@Result(name="success",location="/admin-video-passStatus-yes.jsp",type="redirect"),
		@Result(name="error",location="/admin-login.jsp")
}
)
public String cancelStatus(){
	if(videoId>0){
	
	VideoManager vm=ServiceFactory.getVideoManager();
	vm.cancelStatus(videoId);
	List<Video> videosNoStatusList=vm.findByStatus("yes");
	if(videosNoStatusList!=null){
		ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
		}
	return "success";
	}else
		return "error";
}
@Action(value="/adminVideo/change-yes-no",results={
        @Result(name="passStatus-no",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="passStatus-yes",location="/admin-video-passStatus-yes.jsp",type="redirect")
		
})
public String changeNoYesStatusJsp(){
	
		
	VideoManager vm=ServiceFactory.getVideoManager();
	List<Video> videosNoStatusList;
	if(to.equals("passStatus-yes")){
		videosNoStatusList=vm.findByStatus("yes");
		if(videosNoStatusList!=null){
			ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
		}
		return "passStatus-yes";
	}
	else {
		videosNoStatusList=vm.findByStatus("no");
		if(videosNoStatusList!=null){
			ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
		}
		return "passStatus-no";
	}
	
}
@Action(value="/admin/changePassword",results={
		@Result(name="passStatus-no",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="error",location="/admin-changePassword-error.jsp",type="redirect")
		})
public String changePassword(){
	
	String fielderror="";int flag=0;
	
	if(admin.getPassword().trim().isEmpty()) {fielderror+="密码不能为空"+" ";flag=1;}
	if(!admin.getPassword().trim().isEmpty()&&(admin.getPassword().trim().length()<5||admin.getPassword().trim().length()>12)) {fielderror+="输入的密码长度必须在5到12位"+" ";flag=1;}
	if(newPassword1.trim().isEmpty()){fielderror+="新密码不能为空"+" ";flag=1;}
	if(!newPassword1.trim().isEmpty()&&(newPassword1.length()<5||newPassword1.length()>12) ){fielderror+="输入的新密码长度必须在5到12位"+" ";flag=1;}
	if(!newPassword1.equals(newPassword2)) {fielderror+="两次密码输入不相同"+" ";flag=1;}
	
	if(flag==1){
		ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
		return "error";
	}
	
	Admin admin1=(Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
	if(!admin1.getPassword().equals(admin.getPassword())){
		fielderror+="密码不正确";
		ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
		return "error";
	}else{
		AdminManager am=ServiceFactory.getAdminManager();
		admin1.setPassword(newPassword1);
		am.updateAdmin(admin1);
		ServletActionContext.getRequest().getSession().setAttribute("admin", admin1);
		
		return "passStatus-no";
		
	}
}
//设置新用户名
@Action(value="/admin/update",results={
		@Result(name="passStatus-no",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="error",location="/admin-information-update-error.jsp",type="redirect")
		})
public String update(){
	
	String fielderror="";int flag=0;
	if(newUsername.trim().isEmpty()) {fielderror+="新用户名不能为空"+" ";flag=1;}
	if(!newUsername.trim().isEmpty()&&(newUsername.trim().length()<5||newUsername.trim().length()>12)) {fielderror+="输入的新用户名长度必须在5到12位"+" ";flag=1;}
	if(flag==1){
		ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
		return "error";
	}
	AdminManager am=ServiceFactory.getAdminManager();
	int id=am.validateName(newUsername);
	if(id==0){
		fielderror+="用户名已存在";
		ServletActionContext.getRequest().getSession().setAttribute("fielderror", fielderror);
		return "error";
	}else{
		Admin admin1=(Admin)ServletActionContext.getRequest().getSession().getAttribute("admin");
		admin1.setUsername(newUsername);
		am.updateAdmin(admin1);
		ServletActionContext.getRequest().getSession().setAttribute("admin", admin1);
		
		return "passStatus-no";
	}
}
//设置视频简介
@Action(value="/adminVideo/saveVideoDesc",results={
        @Result(name="passStatus-no",location="/admin-video-passStatus-no.jsp",type="redirect"),
		@Result(name="passStatus-yes",location="/admin-video-passStatus-yes.jsp",type="redirect")
		
})
public String saveVideoDesc() throws IOException{
	
	VideoManager vm=ServiceFactory.getVideoManager();
    Video video=vm.findByVideoId(videoId);
    video.setDesc(videoDesc);
    vm.updateVideo(video);
	List<Video> videosNoStatusList;
	
	to=null;
	if(to.equals("passStatus-yes")){
	     videosNoStatusList=vm.findByStatus("yes");
	}
	else{
		 videosNoStatusList=vm.findByStatus("no");
	}
	if(videosNoStatusList!=null){
		ServletActionContext.getRequest().getSession().setAttribute("videosNoStatusList", videosNoStatusList);
	}
	if(to.equals("passStatus-yes")) return "passStatus-yes";
	else return "passStatus-no";
}
}
