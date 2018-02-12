package net.zypro.istrone.handler;

import java.io.IOException;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

public class UriRequestTask extends AsyncTask<HttpUriRequest,Void,Boolean>{
	private HttpUriRequest mRequest;
	private ResponseHandler mHandler;
	
	public UriRequestTask(HttpUriRequest mRequest, ResponseHandler mHandler) {
		super();
		this.mRequest = mRequest;
		this.mHandler = mHandler;
	}


	public void excute() {
		this.execute(mRequest);
	}

	public Uri getUri() {
        return Uri.parse(mRequest.getURI().toString());
    }

	@Override
	protected Boolean doInBackground(HttpUriRequest... params) {
		HttpResponse response=null;
		
		HttpClient client = new DefaultHttpClient();
		try {
			response=client.execute(mRequest);
			mHandler.handleResponse(response, getUri());
			
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				return true;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result)
	{
		if(requestCallBack!=null)
		{
			if(result)
			{
				requestCallBack.onSuccess();
			}else{
				requestCallBack.onFailed();
			}
		}
	}
	
    
	public interface IRequestCallBack
	{
		void onSuccess();
		void onFailed();
	}
	
	private IRequestCallBack requestCallBack;

	public void setRequestCallBack(IRequestCallBack requestCallBack) {
		this.requestCallBack = requestCallBack;
	}
}
