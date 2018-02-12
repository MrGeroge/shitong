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
	private int id;                   //(���ݿ�����ӣ�
	private String url;              //��Ƶ���ڵ�λ�ã���׿�����ṩ��
	private String cover_url;                 //��Ƶ����
	private String type;//��Ƶ�������Ƶ���ͨ�����ṩ��
	@Column(name="description")
	private String desc;//��Ƶ��˵������׿�����ṩ��
	private Date time;          //��Ƶ����ʱ��
	private int viewNumber=0;          //��Ƶ���Ŵ���
	private int republishedNumber;  //��Ƶת�ش���
	private int sharedNumber;     //��Ƶ�������
	private int lovedNumber;                 //��Ƶ���޵ĸ������༭��Ƶ���账�ã�
	private String status;//��Ƶ��״̬���Ƿ�ͨ����ˣ����༭��Ƶʱ�ṩ��ֵ	
	private String location;          //��Ƶ��λ   ����׿�����ṩ��
	private String tags; //��Ƶ��ǩ ����׿�����ṩ��
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private User user;               //��Ƶ�ϴ����û�
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
