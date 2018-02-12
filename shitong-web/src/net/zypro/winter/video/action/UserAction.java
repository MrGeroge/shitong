package net.zypro.winter.video.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONObject;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.UserInfo;
import net.zypro.winter.video.dao.UserDao;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.service.OSSHelper;
import net.zypro.winter.video.service.UserManager;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private UserInfo userInfo;
	private String vercode;
	private String status;
	private String password;
	private String message;
	private File image;
	private String imageFileName;
	private String imageContentType;
	private String random; // 传递的随机数
	private String username; // 传递的用户名

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVercode() {
		return vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	@Action(value = "/user/register",results={
//		@Result(name = "success", location = "/sendMail.jsp")	
//	})
	@Action(value="/user/register",results={
			@Result(name="success",location="/test.jsp"),@Result(name="failed",location="/register-failed.jsp")
	})
	public String register() throws Exception {// 判断用户名是否已经存在,此邮箱验证已关
//		ServletActionContext.getResponse().setContentType(
//				"application/json;charset=UTF-8");
		
		UserManager um = ServiceFactory.getUserManager();
		Map<String,Object> map=um.addUser(user);
		if((boolean)map.get("status")){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<4;i++){
			Random random=new Random();
			int num=random.nextInt(10);
			sb.append(num);
		}                    //产生4位随机数
		String random=sb.toString();
		ServletActionContext.getRequest().getSession().setAttribute("random",random );     //在session中保存随机数
		ServletActionContext.getRequest().setAttribute("username", user.getUsername());      //在request中保留用户名
//		return "success";
		//JSONObject json=new JSONObject();
//		json.put("status", "success");
//		json.put("message", "用户注册成功");
		return "success";                    //注册成功jsp
		}
		else{
			return "failed";                 //注册失败jsp
		}
		
	}

	@Action(value = "/user/login")
	public void login() throws Exception {
		UserInfo userInfo=new UserInfo();
		JSONObject json = new JSONObject();
		UserManager um = ServiceFactory.getUserManager();// 产生一个user业务逻辑组件
		String sessionId = null;
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		int id = um.checkUser(user); // 检查是否存在此用户
		if (id > 0) {
			user.setId(id);     //确实存在此用户,根据用户user，找到其对应的userInfo
			userInfo=um.findInfoByUser(user);
			user.setUserInfo(userInfo);
			ServletActionContext.getRequest().getSession()
					.setAttribute("user", user);
			sessionId = ServletActionContext.getRequest().getSession().getId();
			status = "success";
			message = "恭喜您成功登陆";
			sessionId = ServletActionContext.getRequest().getSession().getId();
			json.put("avatar", userInfo.getAvatar_url());
			//System.out.println(userInfo.getAvatar_url());
			json.put("nickName",userInfo.getNick_name() );
			json.put("sex", userInfo.getSex());
			json.put("address", userInfo.getAddress());
			json.put("desc", userInfo.getDesc());
			json.put("sessionMaxTime", ServletActionContext.getRequest().getSession().getMaxInactiveInterval());
		} else {
			status = "failed";
			message = "登陆失败，用户名或密码输入错误，请重新输入";
		}
		json.put("sessionId", sessionId);
		json.put("status", status);
		json.put("message", message);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/changePass")
	public void changePass() throws Exception {
		JSONObject json = new JSONObject();
		UserManager um = ServiceFactory.getUserManager();// 获得user的业务逻辑组件
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		User user1 = ((User) (ServletActionContext.getRequest().getSession()
				.getAttribute("user")));
		user1.setPassword(password);
		um.updateUser(user1);
		ServletActionContext.getRequest().getSession()
				.setAttribute("user", user1);
		status = "success";
		message = "用户修改密码成功";
		json.put("status", status);
		json.put("message", message);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/updateInfo")      //用户信息的更新
	public void updateInfo() throws Exception {
		JSONObject json = new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		if(userInfo.getNick_name()==null){
			userInfo.setNick_name("null");
		}
		if(userInfo.getSex()==null){
			userInfo.setSex("null");
		}
		if(userInfo.getAge()==0){
			userInfo.setAge(0);
		}
		if(userInfo.getQq()==null){
			userInfo.setQq("null");
		}
		if(userInfo.getAddress()==null){
			userInfo.setAddress("null");
		}
		if(userInfo.getDesc()==null){
			userInfo.setDesc("null");
		}
		if(userInfo.getAvatar_url()==null){
			userInfo.setAvatar_url("null");
		}
		UserManager um = ServiceFactory.getUserManager();
		
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");     //只要是登陆之后均有userInfo信息，修改之后的userInfo
		int info_id=user.getUserInfo().getId();      //之前存入数据库中的userInfo 的id
		userInfo.setId(info_id);                     //设置id，成为一条新纪录
		um.UpdateInfo(userInfo);      //更新数据库中记录
		user.setUserInfo(userInfo);    //user新的记录
		um.updateUser(user);              //更新user数据库中记录
		ServletActionContext.getRequest().getSession()
				.setAttribute("user", user);
		status = "success";
		message = "用户编辑信息成功";
		json.put("status", status);
		json.put("message", message);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/logout")
	public void logout() throws IOException {
		JSONObject json = new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		ServletActionContext.getRequest().getSession().invalidate();
		json.put("status", "success");
		json.put("message", "用户成功退出系统");
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/image/upload")
	// 用户上传头像
	public void avatarUpload() throws Exception {
		JSONObject json = new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		if(image!=null){    //上传图片有效
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileExt = imageFileName
				.substring(imageFileName.lastIndexOf("."));
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + fileExt;
		FileInputStream is = new FileInputStream(image);
		String URL = OSSHelper.uploadImage(newFileName, is);
		is.close();
		json.put("status", "success");
		json.put("message", "上传图片成功");
		json.put("url", URL);
		}
		else{
		json.put("status", "failed");
		json.put("message", "上传图片失败，用户未选择上传的图片,请重新上传");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/detail")
	public void userDetail() throws Exception {
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		String username = user.getUsername();
		UserManager um = ServiceFactory.getUserManager();
		List<User> users = um.findAllUsers();
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				UserInfo userInfo = user.getUserInfo();
				JSONObject json = new JSONObject();
				json.put("avatar", userInfo.getAvatar_url());
				json.put("nickName", userInfo.getNick_name());
				json.put("sex", userInfo.getSex());
				json.put("address", userInfo.getAddress());
				json.put("desc", userInfo.getDesc());
				ServletActionContext.getResponse().getWriter()
						.println(json.toString());
			}
		}
	}

	@Action(value = "/user/activated")
	public void activated() throws Exception { // 用于账号的激活
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		String randomNum = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("random"); // 获得与用户名对应的随机数
		if (randomNum.equals(random)) { // 验证通过，允许激活
			UserManager userManager = ServiceFactory.getUserManager();
			userManager.updateActive(username); // 请求参数username,激活用户名
			json.put("status", "success");
			json.put("message", "用户名激活成功");
		} else{
			json.put("status", "failed");
			json.put("message", "用户名激活失败");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());	
	}
	@Action(value="/upload/image")
	public void upload() throws Exception{            //不用阿里云上传，上传头像
	    JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		if(image!=null){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileExt=imageFileName.substring(imageFileName.lastIndexOf("."));
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) +  fileExt;
		String uploadPath=ServletActionContext.getServletContext().getRealPath("/image");
		String saveURL=uploadPath+"/"+newFileName;
		FileInputStream is=new FileInputStream(image);
		FileOutputStream os=new FileOutputStream(saveURL);
		byte[] buff=new byte[1024];
		int hasRead=0;
		while((hasRead=is.read(buff))>0){
			os.write(buff, 0, hasRead);			
		}
		is.close();
		os.close();
		String path=ServletActionContext.getRequest().getContextPath();
		String basePath=ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+path;
		String URL=basePath+"/image/"+newFileName;
		json.put("status", "success");
		json.put("url", URL);
		json.put("message", "用户视频上传成功");
		}
		else{
			json.put("status", "failed");
			json.put("message","用户上传视频失败，用户未选择上传的视频,请重新上传" );
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
}
