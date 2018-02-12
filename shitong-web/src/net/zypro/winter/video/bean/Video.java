package net.zypro.winter.video.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
@Entity
@Table(name="video")
public class Video {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;                   //(数据库中添加）
	private String url;              //视频所在的位置（安卓界面提供）
	private String cover_url;                 //视频封面
	private String type;//视频的类别（视频审核通过后提供）
	@Column(name="description")
	private String desc;//视频的说明（安卓界面提供）
	private Date time;          //视频发布时间
	private int viewNumber=0;          //视频播放次数
	private int republishedNumber;  //视频转载次数
	private int sharedNumber;     //视频分享次数
	private int lovedNumber;                 //视频点赞的个数（编辑视频赋予处置）
	private String status;//视频的状态（是否通过审核）（编辑视频时提供初值	
	private String location;          //视频定位   （安卓界面提供）
	private String tags; //视频标签 （安卓界面提供）
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private User user;               //视频上传的用户
	@OneToMany(mappedBy="video",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Comment> comments=new ArrayList<>();
	public Video(){
		
	}
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getViewNumber() {
		return viewNumber;
	}
	public void setViewNumber(int viewNumber) {
		this.viewNumber = viewNumber;
	}
	public int getRepublishedNumber() {
		return republishedNumber;
	}
	public void setRepublishedNumber(int republishedNumber) {
		this.republishedNumber = republishedNumber;
	}
	public int getSharedNumber() {
		return sharedNumber;
	}
	public void setSharedNumber(int sharedNumber) {
		this.sharedNumber = sharedNumber;
	}
	public int getLovedNumber() {
		return lovedNumber;
	}
	public void setLovedNumber(int lovedNumber) {
		this.lovedNumber = lovedNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getTag() {
		return tags;
	}



	public void setTag(String tag) {
		this.tags = tag;
	}



	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public int getCommentNumber(){
		return comments.size();
	}
	public String getCover_url() {
		return cover_url;
	}
	public void setCover_url(String cover_url) {
		this.cover_url = cover_url;
	}
	
}
