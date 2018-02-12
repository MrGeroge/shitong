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
	private String random; // ���ݵ������
	private String username; // ���ݵ��û���

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
	public String register() throws Exception {// �ж��û����Ƿ��Ѿ�����,��������֤�ѹ�
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
		}                    //����4λ�����
		String random=sb.toString();
		ServletActionContext.getRequest().getSession().setAttribute("random",random );     //��session�б��������
		ServletActionContext.getRequest().setAttribute("username", user.getUsername());      //��request�б����û���
//		return "success";
		//JSONObject json=new JSONObject();
//		json.put("status", "success");
//		json.put("message", "�û�ע��ɹ�");
		return "success";                    //ע��ɹ�jsp
		}
		else{
			return "failed";                 //ע��ʧ��jsp
		}
		
	}

	@Action(value = "/user/login")
	public void login() throws Exception {
		UserInfo userInfo=new UserInfo();
		JSONObject json = new JSONObject();
		UserManager um = ServiceFactory.getUserManager();// ����һ��userҵ���߼����
		String sessionId = null;
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		int id = um.checkUser(user); // ����Ƿ���ڴ��û�
		if (id > 0) {
			user.setId(id);     //ȷʵ���ڴ��û�,�����û�user���ҵ����Ӧ��userInfo
			userInfo=um.findInfoByUser(user);
			user.setUserInfo(userInfo);
			ServletActionContext.getRequest().getSession()
					.setAttribute("user", user);
			sessionId = ServletActionContext.getRequest().getSession().getId();
			status = "success";
			message = "��ϲ���ɹ���½";
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
			message = "��½ʧ�ܣ��û��������������������������";
		}
		json.put("sessionId", sessionId);
		json.put("status", status);
		json.put("message", message);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/changePass")
	public void changePass() throws Exception {
		JSONObject json = new JSONObject();
		UserManager um = ServiceFactory.getUserManager();// ���user��ҵ���߼����
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		User user1 = ((User) (ServletActionContext.getRequest().getSession()
				.getAttribute("user")));
		user1.setPassword(password);
		um.updateUser(user1);
		ServletActionContext.getRequest().getSession()
				.setAttribute("user", user1);
		status = "success";
		message = "�û��޸�����ɹ�";
		json.put("status", status);
		json.put("message", message);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/user/updateInfo")      //�û���Ϣ�ĸ���
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
				.getAttribute("user");     //ֻҪ�ǵ�½֮�����userInfo��Ϣ���޸�֮���userInfo
		int info_id=user.getUserInfo().getId();      //֮ǰ�������ݿ��е�userInfo ��id
		userInfo.setId(info_id);                     //����id����Ϊһ���¼�¼
		um.UpdateInfo(userInfo);      //�������ݿ��м�¼
		user.setUserInfo(userInfo);    //user�µļ�¼
		um.updateUser(user);              //����user���ݿ��м�¼
		ServletActionContext.getRequest().getSession()
				.setAttribute("user", user);
		status = "success";
		message = "�û��༭��Ϣ�ɹ�";
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
		json.put("message", "�û��ɹ��˳�ϵͳ");
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}

	@Action(value = "/image/upload")
	// �û��ϴ�ͷ��
	public void avatarUpload() throws Exception {
		JSONObject json = new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		if(image!=null){    //�ϴ�ͼƬ��Ч
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileExt = imageFileName
				.substring(imageFileName.lastIndexOf("."));
		String newFileName = df.format(new Date()) + "_"
				+ new Random().nextInt(1000) + fileExt;
		FileInputStream is = new FileInputStream(image);
		String URL = OSSHelper.uploadImage(newFileName, is);
		is.close();
		json.put("status", "success");
		json.put("message", "�ϴ�ͼƬ�ɹ�");
		json.put("url", URL);
		}
		else{
		json.put("status", "failed");
		json.put("message", "�ϴ�ͼƬʧ�ܣ��û�δѡ���ϴ���ͼƬ,�������ϴ�");
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
	public void activated() throws Exception { // �����˺ŵļ���
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType(
				"application/json;charset=UTF-8");
		String randomNum = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("random"); // ������û�����Ӧ�������
		if (randomNum.equals(random)) { // ��֤ͨ����������
			UserManager userManager = ServiceFactory.getUserManager();
			userManager.updateActive(username); // �������username,�����û���
			json.put("status", "success");
			json.put("message", "�û�������ɹ�");
		} else{
			json.put("status", "failed");
			json.put("message", "�û�������ʧ��");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());	
	}
	@Action(value="/upload/image")
	public void upload() throws Exception{            //���ð������ϴ����ϴ�ͷ��
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
		json.put("message", "�û���Ƶ�ϴ��ɹ�");
		}
		else{
			json.put("status", "failed");
			json.put("message","�û��ϴ���Ƶʧ�ܣ��û�δѡ���ϴ�����Ƶ,�������ϴ�" );
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
}
