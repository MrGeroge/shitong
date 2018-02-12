package net.zypro.winter.video.bean;

import javax.persistence.*;

@Entity
@Table(name="comment")
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private int id;
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
private User user;
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.EAGER)
private Video video;
private String content;

public Comment(int id, String content) {
	super();
	this.id = id;
	this.content = content;
}
public Comment(){
	
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
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

}
