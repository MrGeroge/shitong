package net.zypro.winter.video.bean;

public class UserWithNameAvatarNick {
  private String username;
  private String nickname;
  private String avatar;
  public UserWithNameAvatarNick(){
	  
  }
public UserWithNameAvatarNick(String username, String nickname, String avatar) {
	super();
	this.username = username;
	this.nickname = nickname;
	this.avatar = avatar;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getAvatar() {
	return avatar;
}
public void setAvatar(String avatar) {
	this.avatar = avatar;
}

}
