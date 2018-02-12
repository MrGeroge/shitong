package net.zypro.winter.video.bean;

import javax.persistence.*;

@Entity
@Table(name="collect")
public class Collect {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;   //�Ѳؼ�¼
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private User user;//user��������һ
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private Video video;//video��������һ
	public Collect(int id) {
		super();
		this.id = id;
	}
	public Collect(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
}
