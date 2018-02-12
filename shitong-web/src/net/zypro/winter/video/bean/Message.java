package net.zypro.winter.video.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id;
	@Column(name="sender_id")
   private int senderId;
	@Column(name="receiver_id")
   private int receiverId;
   private String title;
   private String content;
   public Message(){
	   
   }
public Message(int id, int senderId, int receiverId, String title,
		String content) {
	super();
	this.id = id;
	this.senderId = senderId;
	this.receiverId = receiverId;
	this.title = title;
	this.content = content;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getSenderId() {
	return senderId;
}
public void setSenderId(int senderId) {
	this.senderId = senderId;
}
public int getReceiverId() {
	return receiverId;
}
public void setReceiverId(int receiverId) {
	this.receiverId = receiverId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
   
}
