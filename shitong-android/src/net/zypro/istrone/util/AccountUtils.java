package net.zypro.istrone.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.zypro.istrone.model.Account;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class AccountUtils {

	private static final String ACCOUNT_INFO_FILE="account.dat";
	
    private static final String PREFIX_PREF_USERNAME = "username";
    private static final String PREFIX_PREF_NICKNAME = "nickname";
    private static final String PREFIX_PREF_AVATAR_URL = "avatar_url";
    private static final String PREFIX_PREF_SEX = "sex";
    private static final String PREFIX_PREF_ADDRESS= "address";
    private static final String PREFIX_PREF_DESC= "desc";
    
    private static String tokenHolder;
	
	private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
	
	public static void saveAccount(String password,String token,Context ctx)
	{
		Account account=new Account();
		account.timestamp=TimeUtils.getExactTime();
		account.password=password;
		account.token=token;
		
		try{
			FileOutputStream fos=ctx.openFileOutput(ACCOUNT_INFO_FILE,ctx.MODE_PRIVATE);
	        ObjectOutputStream out=new ObjectOutputStream(fos);
	        out.writeObject(account);
	        out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Account readAccount(Context ctx)
	{
		try{
			FileInputStream fis=ctx.openFileInput(ACCOUNT_INFO_FILE);
			ObjectInputStream in=new ObjectInputStream(fis);
			Account user=(Account)in.readObject();
			in.close();
			
			return user;
		}catch(Exception e){
			e.printStackTrace();
	    }
		
		return null;
	}
	
			
	public static boolean hasLocalAccount(Context ctx)
	{
		return !TextUtils.isEmpty(getUsername(ctx));
	}
	
	public static String getToken(Context ctx)
	{
		if(tokenHolder==null)
		{
			Account account=readAccount(ctx);
			tokenHolder=account.token;
		}
		
		return tokenHolder;
	}
	
	public static String getUsername(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_USERNAME, null);
	}
	
	public static String getNickname(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_NICKNAME, null);
	}
	
	public static String getAvatarUrl(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_AVATAR_URL, null);
	}
	
	public static String getSex(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_SEX, null);
	}
	
	public static String getAddress(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_ADDRESS, null);
	}
	
	public static String getDesc(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_DESC, null);
	}
	
		
	public static void setUsername(Context ctx,String username)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_USERNAME, username).commit();
	}
	
	public static void setNickname(Context ctx,String nickname)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_NICKNAME, nickname).commit();
	}
	
	public static void setAvatarUrl(Context ctx,String avatar_url)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_AVATAR_URL,avatar_url).commit();
	}
	
	public static void setSex(Context ctx,String sex)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_SEX,sex).commit();
	}
	
	public static void setAddress(Context ctx,String a)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_ADDRESS,a).commit();
	}
	
	public static void setDesc(Context ctx,String d)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_DESC,d).commit();
	}
	
	public static void cleaAccount(Context ctx)
	{
		ctx.deleteFile(ACCOUNT_INFO_FILE);
		tokenHolder=null;
		setUsername(ctx,"");
		setNickname(ctx,"");
	}
}
