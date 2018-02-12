package net.zypro.winter.video.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Love {     //点赞
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
private User user;    //多对一的关系
@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
private Video video;
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
