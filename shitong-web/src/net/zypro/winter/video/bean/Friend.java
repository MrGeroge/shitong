package net.zypro.winter.video.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="friend")
public class Friend {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="user_id")
	private int userId;
	@Column(name="fri_user_id")
	private int friUserId;

	public Friend(){
		
	}
	
	public Friend(int id, int userId, int friUserId) {
		super();
		this.id = id;
		this.userId = userId;
		this.friUserId = friUserId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFriUserId() {
		return friUserId;
	}

	public void setFriUserId(int friUserId) {
		this.friUserId = friUserId;
	}
}
