package net.zypro.istrone.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class NetUtils {
	private static final String PREFIX_PREF_SEVER_ADDRESS = "server_addr";
	
	private static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
	
	public static String getServerAddr(Context ctx)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        return sp.getString(PREFIX_PREF_SEVER_ADDRESS, null);
	}
	
	public static void setServerAddress(Context ctx,String addr)
	{
		SharedPreferences sp = getSharedPreferences(ctx);
        sp.edit().putString(PREFIX_PREF_SEVER_ADDRESS, addr).commit();
	}
	
	public static String buidUrlWithToken(String url,String token)
	{
		return url+";jsessionid="+token;
	}
	
	public static HttpGet makeGetRequest(String url, Map<String, String> params)
	{
		if(params!=null){
    		StringBuilder sb=new StringBuilder(url);
    		sb.append("?");
    		
    		for (Entry<String, String> entry: params.entrySet()) {
    			sb.append(entry.getKey());
    			sb.append("=");    			
    			sb.append(entry.getValue());
    			sb.append("&");
    		}
    		
    		sb.delete(sb.length()-1, sb.length());
    		url=sb.toString();
    	}
		
		return new HttpGet(url);
	}
	
	public static HttpPost makePostRequest(String url, Map<String, String> params)
	{
		HttpPost httpRequest=new HttpPost(url);
    	
    	if(params!=null){
    		List<NameValuePair> urlParams=new ArrayList<NameValuePair>();
    		
    		for (Entry<String, String> entry: params.entrySet()) {
                
    			urlParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

    		}
    		
    		try {
				httpRequest.setEntity(new UrlEncodedFormEntity(urlParams,"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return httpRequest;
	}
	
	
	public static HttpPost makePostRequest(String url,String key, File file)
	{
		FileBody bin=new FileBody(file);
		
    	HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart(key, bin)
                .build();
    	
    	HttpPost httpRequest=new HttpPost(url);
    	httpRequest.setEntity(reqEntity);
    	
    	return httpRequest;
	}
}
