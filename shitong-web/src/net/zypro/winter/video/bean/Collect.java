package net.zypro.winter.video.bean;

import javax.persistence.*;

@Entity
@Table(name="collect")
public class Collect {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;   //搜藏记录
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private User user;//user是外键多对一
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
	private Video video;//video是外键多对一
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
