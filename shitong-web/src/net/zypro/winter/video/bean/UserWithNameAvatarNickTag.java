package net.zypro.winter.video.bean;

public class UserWithNameAvatarNickTag {
	private String username;
	  private String nickname;
	  private String avatar;
	  private String tag;
	  public UserWithNameAvatarNickTag(){
		  
	  }
	public UserWithNameAvatarNickTag(String username, String nickname,
			String avatar, String tag) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.avatar = avatar;
		this.tag = tag;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	  
}
