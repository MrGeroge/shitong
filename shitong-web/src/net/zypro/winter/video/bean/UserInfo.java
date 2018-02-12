package net.zypro.winter.video.bean;

import javax.persistence.*;
@Entity
@Table(name="user_info")
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nick_name;
	private String sex;
	private int age;
	private String qq;
	private String   avatar_url;
	private String address;
	@Column(name="description")
	private String desc;
	
	
	

	public UserInfo(int id, String nick_name, String sex, int age, String qq,
			String avatar_url, String address, String desc) {
		super();
		this.id = id;
		this.nick_name = nick_name;
		this.sex = sex;
		this.age = age;
		this.qq = qq;
		this.avatar_url = avatar_url;
		this.address = address;
		this.desc = desc;
	}
	public UserInfo(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
