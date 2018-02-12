package net.zypro.istrone.model;

public class Video {
	public int id;
	public String username;
	public String nickname;
	public String avatar_url;
	public String time;
	public String desc;
	public String video_url;
	public String cover_url;
	public String tag;	
	
	public int viewNumber; //播放次数
	public int republishedNumber; //转载次数
	public int sharedNumber; //分享次数
	public int lovedNumber; //点赞次数
	public int commentNumber;
}
