package net.zypro.istrone.util;

import java.io.File;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class AppConfig {
	public static void checkAppDir(String path)
    {
    	File dir=new File(path);
    	
    	if(!dir.exists())
    	{
    		dir.mkdirs();
    	}
    }
	
	static
	{
		checkAppDir(ExternalFile.APP_SDCARD_ROOT);
		checkAppDir(ExternalFile.CACHE_PATH);
		checkAppDir(ExternalFile.VIDEO_DRAFT_PATH);
	}
	
	public static final class ExternalFile
	{
		 public final static String APP_SDCARD_ROOT=Environment.getExternalStorageDirectory().getPath()+"/net.zypro.istrone/";
	     public final static String CACHE_PATH=APP_SDCARD_ROOT+"cache/";
	     public final static String VIDEO_DRAFT_PATH=APP_SDCARD_ROOT+"drafts/";
	}
	
	
	public static final class URL
    {
		public static void reset()
		{
			 LOGIN_URL=BASE_URL+"/user/login";
		     LOGOUT_URL=BASE_URL+"/user/logout";
		     SIGIN_URL=BASE_URL+"/user/register";
		     UPDATA_INFO_URL=BASE_URL+"/user/updateInfo";
		         
		     GET_FOLLOWER_URL=BASE_URL+"/friend/getFollowers";
		     GET_FOLLOWING_URL=BASE_URL+"/friend/getFollowing";
		     FIND_FRIEND_URL=BASE_URL+"/friend/find";
		     DELETE_FRIEND_URL=BASE_URL+"/friend/delete";
		     ADD_FRIEND_URL=BASE_URL+"/friend/add";
		        
		     IMAGE_UPLOAD_URL=BASE_URL+"/upload/image";
		     VIDEO_UPLOAD_URL=BASE_URL+"/upload/video";
		         
		     LIST_VIDEO_URL=BASE_URL+"/video/list";
		}
		
    	public static  String BASE_URL="http://winter-video.aliapp.com/video.demo";
        public static  String LOGIN_URL=BASE_URL+"/user/login";
        public static  String LOGOUT_URL=BASE_URL+"/user/logout";
        public static  String SIGIN_URL=BASE_URL+"/user/register";
        public static  String UPDATA_INFO_URL=BASE_URL+"/user/updateInfo";
         
        public static  String GET_FOLLOWER_URL=BASE_URL+"/friend/getFollowers";
        public static  String GET_FOLLOWING_URL=BASE_URL+"/friend/getFollowing";
        public static  String FIND_FRIEND_URL=BASE_URL+"/friend/find";
        public static  String DELETE_FRIEND_URL=BASE_URL+"/friend/delete";
        public static  String ADD_FRIEND_URL=BASE_URL+"/friend/add";
        
        public static  String IMAGE_UPLOAD_URL=BASE_URL+"/upload/image";
        public static  String VIDEO_UPLOAD_URL=BASE_URL+"/video/upload";
         
        public static  String LIST_VIDEO_URL=BASE_URL+"/video/list";
        public static String ADD_VIDEO_URL=BASE_URL+"/video/add";
    }
	
	public static boolean isConnect(Context context) { 
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理） 
	    try { 
	        ConnectivityManager connectivity = (ConnectivityManager) context 
	                .getSystemService(Context.CONNECTIVITY_SERVICE); 
	        if (connectivity != null) { 
	            // 获取网络连接管理的对象 
	            NetworkInfo info = connectivity.getActiveNetworkInfo(); 
	            if (info != null&& info.isConnected()) { 
	                // 判断当前网络是否已经连接 
	                if (info.getState() == NetworkInfo.State.CONNECTED) { 
	                    return true; 
	                } 
	            } 
	        } 
	    } catch (Exception e) { 
	        return false; 
	    } 
	    
	    return false;
	}
}
