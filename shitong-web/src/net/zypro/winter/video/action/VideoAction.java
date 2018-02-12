package net.zypro.winter.video.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.service.OSSHelper;
import net.zypro.winter.video.service.VideoManager;


import net.zypro.winter.video.util.PageUtil;

import com.opensymphony.xwork2.ActionSupport;

public class VideoAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File video;
	private String videoContentType;
	private String videoFileName;
	private Video video1; //�û��ڱ�����video����Ϣ��ͨ��video��װ
	private String keyword;
	//private String tag;//������tag
	private String username;
	private int beginIndex;
	private int count;
	public File getVideo() {
		return video;
	}
	public void setVideo(File video) {
		this.video = video;
	}
	public String getVideoContentType() {
		return videoContentType;
	}
	public void setVideoContentType(String videoContentType) {
		this.videoContentType = videoContentType;
	}
	public String getVideoFileName() {
		return videoFileName;
	}
	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	
	public Video getVideo1() {
		return video1;
	}
	public void setVideo1(Video video1) {
		this.video1 = video1;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
//	public String getTag() {
//		return tag;
//	}
//	public void setTag(String tag) {
//		this.tag = tag;
//	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Action(value="/video/upload")
	public void upload() throws Exception{            //���ð������ϴ����ϴ�ͷ��
	    JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		if(video!=null){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileExt=videoFileName.substring(videoFileName.lastIndexOf("."));
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) +  fileExt;
		String uploadPath=ServletActionContext.getServletContext().getRealPath("/video");
		String saveURL=uploadPath+"/"+newFileName;
		FileInputStream is=new FileInputStream(video);
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
		String URL=basePath+"/video/"+newFileName;
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
	@Action(value="/upload/video",results={@Result(name="input", location="/video-input.jsp")})
	public void upload1() throws Exception{                 //ͨ���������ϴ���Ƶ
	    JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		if(video!=null){           //�ϴ���Ƶ��Ч
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileExt=videoFileName.substring(videoFileName.lastIndexOf("."));
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) +  fileExt;
		FileInputStream is=new FileInputStream(video);
		String URL=OSSHelper.uploadVideo(newFileName, is);
		is.close();
		json.put("status", "success");
		json.put("message", "�û��ϴ���Ƶ�ɹ�");
		json.put("url", URL);
		}
		else{
			json.put("status", "failed");
			json.put("message", "�û��ϴ���Ƶʧ�ܣ��û�δѡ���ϴ�����Ƶ,�������ϴ�");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/add")
	public void addVideo() throws Exception{//�༭video�����������ݿ�
		if(video1.getUrl()==null){
			video1.setUrl("null");
		}
		if(video1.getCover_url()==null){
			video1.setCover_url("null");
		}
		if(video1.getUrl()==null){
			video1.setUrl("null");
		}
		if(video1.getTag()==null){
			video1.setTag("null");
		}
		if(video1.getDesc()==null){
			video1.setDesc("null");
		}
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
	    VideoManager vm=ServiceFactory.getVideoManager();
		video1.setStatus("no");
		video1.setTime(new Date());
		video1.setLovedNumber(0);
		video1.setRepublishedNumber(0);
		video1.setSharedNumber(0);
		video1.setViewNumber(0);
		video1.setComments(null);
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user!=null){
		video1.setUser(user);
		vm.addVideo(video1);  //�����Ƶ
		json.put("status", "success");
		json.put("message", "��Ƶ��ӳɹ�");
		}
		else
		{
			json.put("status", "failed");
			json.put("message", "session��ʧЧ�������µ�½");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/delete")
	public void deleteVideo()throws Exception{   //Ĭ��ͨ��video_id
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		VideoManager vm=ServiceFactory.getVideoManager();
		vm.deleteByVideoId(video1.getId());
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("message", "ɾ���ɹ�");
		ServletActionContext.getResponse().getWriter().println(json.toString());	
	}
	@Action("/video/list")
	public void listVideos() throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		PageUtil page=new PageUtil();
		page.setBeginIndex(beginIndex);
		page.setEveryPage(count);
		VideoManager vm=ServiceFactory.getVideoManager();
		List<Video> list=vm.findAllVideos(page);  //�������ݿ�������video
			for(Video video:list){//ÿһ��json����һ��video
				JSONObject json=new JSONObject();
				vm.addViewNumber(video);
				json.put("id", video.getId());
				json.put("username", video.getUser().getUsername());
				json.put("nickname", video.getUser().getUserInfo().getNick_name());
				json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
				json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
				json.put("desc",video.getDesc());
				json.put("tag", video.getTag());
				json.put("video_url", video.getUrl());
				json.put("cover_url", video.getCover_url());
				video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
				json.put("viewNumber", video.getViewNumber());
				json.put("republishedNumber", video.getRepublishedNumber());
				json.put("sharedNumber", video.getSharedNumber());
				json.put("lovedNumber", video.getLovedNumber());
				video.setComments(vm.findCommentsByVideoId(video.getId()));
				json.put("commentNumber", video.getCommentNumber());
				jsonlist.add(json);
			}
			JSONArray jsonArray=new JSONArray(jsonlist);
			JSONObject json=new JSONObject();
			json.put("status", "success");
			json.put("array", jsonArray);
			ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/find")
	public void findVideo()throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		PageUtil page=new PageUtil();
		page.setBeginIndex(beginIndex);
		page.setEveryPage(count);
		VideoManager vm=ServiceFactory.getVideoManager();//��ô���video��ҵ���߼����
		List<Video> list=vm.findBykeyWord(page,keyword);//ͨ���ؼ���������õ�list
		for(Video video:list){//ÿһ��json����һ��video
			JSONObject json=new JSONObject();
			vm.addViewNumber(video);
			json.put("id", video.getId());
			json.put("username", video.getUser().getUsername());
			json.put("nickname", video.getUser().getUserInfo().getNick_name());
			json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
			json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
			json.put("desc",video.getDesc());
			json.put("tag", video.getTag());
			json.put("video_url", video.getUrl());
			json.put("cover_url", video.getCover_url());
			video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
			json.put("viewNumber", video.getViewNumber());
			json.put("republishedNumber", video.getRepublishedNumber());
			json.put("sharedNumber", video.getSharedNumber());
			json.put("lovedNumber", video.getLovedNumber());
			video.setComments(vm.findCommentsByVideoId(video.getId()));
			json.put("commentNumber", video.getCommentNumber());
			jsonlist.add(json);
		}
		JSONArray jsonArray=new JSONArray(jsonlist);
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("array", jsonArray);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/tag/find")
    public void findByTags()throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		PageUtil page=new PageUtil();
		page.setBeginIndex(beginIndex);
		page.setEveryPage(count);
		VideoManager vm=ServiceFactory.getVideoManager();//��ô���video��ҵ���߼����
		List<Video> list=vm.findByTag(page,video1.getTag());//tagΪ�����ǩ��ɵı�ǩ�ַ���
		for(Video video:list){//ÿһ��json����һ��video
			JSONObject json=new JSONObject();
			vm.addViewNumber(video);
			json.put("id", video.getId());
			json.put("username", video.getUser().getUsername());
			json.put("nickname", video.getUser().getUserInfo().getNick_name());
			json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
			json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
			json.put("desc",video.getDesc());
			json.put("tag", video.getTag());
			json.put("video_url", video.getUrl());
			json.put("cover_url", video.getCover_url());
			video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
			json.put("viewNumber", video.getViewNumber());
			json.put("republishedNumber", video.getRepublishedNumber());
			json.put("sharedNumber", video.getSharedNumber());
			json.put("lovedNumber", video.getLovedNumber());
			video.setComments(vm.findCommentsByVideoId(video.getId()));
			json.put("commentNumber", video.getCommentNumber());
			jsonlist.add(json);
		}
		JSONArray jsonArray=new JSONArray(jsonlist);
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("array", jsonArray);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/user/list")
	public void findByUserName()throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		PageUtil page=new PageUtil();
		page.setBeginIndex(beginIndex);
		page.setEveryPage(count);
		VideoManager vm=ServiceFactory.getVideoManager();//��ô���video��ҵ���߼����
		List<Video> list=vm.findByUserName(page,username);
		for(Video video:list){//ÿһ��json����һ��video
			JSONObject json=new JSONObject();
			vm.addViewNumber(video);    //�ø�video����Ӧ��viewNumber+1
			json.put("id", video.getId());
			json.put("username", video.getUser().getUsername());
			json.put("nickname", video.getUser().getUserInfo().getNick_name());
			json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
			json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
			json.put("desc",video.getDesc());
			json.put("tag", video.getTag());
			json.put("video_url", video.getUrl());
			json.put("cover_url", video.getCover_url());
			video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
			json.put("viewNumber", video.getViewNumber());
			json.put("republishedNumber", video.getRepublishedNumber());
			json.put("sharedNumber", video.getSharedNumber());
			json.put("lovedNumber", video.getLovedNumber());
			video.setComments(vm.findCommentsByVideoId(video.getId()));
			json.put("commentNumber", video.getCommentNumber());
			jsonlist.add(json);
		}
		JSONArray jsonArray=new JSONArray(jsonlist);
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("array", jsonArray);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/repulished/list")
	public void findByUserNameWithRepulished()throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		PageUtil page=new PageUtil();
		page.setBeginIndex(beginIndex);
		page.setEveryPage(count);
		VideoManager vm=ServiceFactory.getVideoManager();//��ô���video��ҵ���߼����
		List<Video> list=vm.findByUserNameWithRepulished(page,username);
		for(Video video:list){//ÿһ��json����һ��video
			JSONObject json=new JSONObject();
			vm.addViewNumber(video);
			json.put("id", video.getId());
			json.put("username", video.getUser().getUsername());
			json.put("nickname", video.getUser().getUserInfo().getNick_name());
			json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
			json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
			json.put("desc",video.getDesc());
			json.put("tag", video.getTag());
			json.put("video_url", video.getUrl());
			json.put("cover_url", video.getCover_url());
			video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
			json.put("viewNumber", video.getViewNumber());
			json.put("republishedNumber", video.getRepublishedNumber());
			json.put("sharedNumber", video.getSharedNumber());
			json.put("lovedNumber", video.getLovedNumber());
			video.setComments(vm.findCommentsByVideoId(video.getId()));
			json.put("commentNumber", video.getCommentNumber());
			jsonlist.add(json);
		}
		JSONArray jsonArray=new JSONArray(jsonlist);
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("array", jsonArray);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/recommend/list")
	public void findByRecommend()throws Exception{
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		List<JSONObject> jsonlist=new ArrayList<>();
		VideoManager vm=ServiceFactory.getVideoManager();//��ô���video��ҵ���߼����
        List<Video> list=vm.findByRecommend();   //��ñ��Ƽ�����Ƶ�б�
        for(Video video:list){//ÿһ��json����һ��video
			JSONObject json=new JSONObject();
			vm.addViewNumber(video);
			json.put("id", video.getId());
			json.put("username", video.getUser().getUsername());
			json.put("nickname", video.getUser().getUserInfo().getNick_name());
			json.put("avatar",video.getUser().getUserInfo().getAvatar_url());
			json.put("time", new SimpleDateFormat("yyyy/MM/dd hh:mm").format(video.getTime()));
			json.put("desc",video.getDesc());
			json.put("tag", video.getTag());
			json.put("video_url", video.getUrl());
			json.put("cover_url", video.getCover_url());
			video.setViewNumber(video.getViewNumber()+1);   //ÿ�λ�ȡһ����Ƶ�б��򲥷�һ��
			json.put("viewNumber", video.getViewNumber());
			json.put("republishedNumber", video.getRepublishedNumber());
			json.put("sharedNumber", video.getSharedNumber());
			json.put("lovedNumber", video.getLovedNumber());
			video.setComments(vm.findCommentsByVideoId(video.getId()));
			json.put("commentNumber", video.getCommentNumber());
			jsonlist.add(json);
		}
		JSONArray jsonArray=new JSONArray(jsonlist);
		JSONObject json=new JSONObject();
		json.put("status", "success");
		json.put("array", jsonArray);
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/addLove")   //���޵Ŀ�����
	public void addLove() throws Exception{
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		VideoManager vm=ServiceFactory.getVideoManager();
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		boolean tag=vm.addLove(user, video1);
		if(tag){
			json.put("status","success");
			json.put("message", "�û����޳ɹ�");
		}
		else{
			json.put("status", "failed");
			json.put("message", "�û�����ʧ��");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/addRepublish")   //ת���Ŀ�����
	public void addRepublish() throws Exception{
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		VideoManager vm=ServiceFactory.getVideoManager();
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		boolean tag=vm.addRepublish(user, video1);
		if(tag){
			json.put("status","success");
			json.put("message", "�û�ת���ɹ�");
		}
		else{
			json.put("status", "failed");
			json.put("message", "�û�ת��ʧ��");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	@Action("/video/addShare")   //���޵Ŀ�����
	public void addShare() throws Exception{
		JSONObject json=new JSONObject();
		ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
		VideoManager vm=ServiceFactory.getVideoManager();
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("user");
		boolean tag=vm.addShare(user, video1);
		if(tag){
			json.put("status","success");
			json.put("message", "�û�����ɹ�");
		}
		else{
			json.put("status", "failed");
			json.put("message", "�û�����ʧ��");
		}
		ServletActionContext.getResponse().getWriter().println(json.toString());
	}
	}
