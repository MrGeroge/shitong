package net.zypro.istrone.handler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import android.net.Uri;

public class StringHandler implements ResponseHandler{
    private String result;
	
	@Override
	public void handleResponse(HttpResponse response, Uri uri)
			throws IOException {
		result=EntityUtils.toString(response.getEntity());
	}
    
	public String getReuslt()
	{
		return result;
	}
}
