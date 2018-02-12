package net.zypro.istrone.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONException;
import org.json.JSONObject;

import net.zypro.istrone.handler.StringHandler;
import net.zypro.istrone.handler.UriRequestTask;
import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ImageView; 
import android.widget.Toast;

public class VideoPublishActivity extends Activity {
	
	private String coverPath;
	private String videoPath;
	
	
	private EditText descEditTxt;
	private EditText tagsEditTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_publish);
		
		coverPath=getIntent().getStringExtra("coverPath");
		videoPath=getIntent().getStringExtra("videoPath");
		
		((ImageView)findViewById(R.id.videoCover)).setImageBitmap(BitmapFactory.decodeFile(coverPath));
		descEditTxt=(EditText)findViewById(R.id.description);
		tagsEditTxt=(EditText)findViewById(R.id.tags);
		
		
		((Button)findViewById(R.id.publish)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				publish();
			}
		});
	}
	
	private void makeHint(String content)
	{
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}
   
	private void publish()
	{
		final String desc=descEditTxt.getText().toString().trim();
		if(TextUtils.isEmpty(desc))
		{
			makeHint("请输入视频描述");
			return;
		}
		
		final String tags=tagsEditTxt.getText().toString().trim();
		if(TextUtils.isEmpty(tags))
		{
			makeHint("请输入视频标签");
			return;
		}
		
		final ProgressDialog mProgressDialog=ProgressDialog.show(this, "发布中...", "正在上传图片...");
		//上传封面
		File image=new File(coverPath);
        final StringHandler mHandler=new StringHandler();
        HttpUriRequest request=NetUtils.makePostRequest(AppConfig.URL.IMAGE_UPLOAD_URL,"image",image);	
		UriRequestTask task=new UriRequestTask(request,mHandler);
        
		task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {

			@Override
			public void onSuccess() {
				Log.d("Upload",mHandler.getReuslt());				
				
				try{
					JSONObject coverUploadResult=new JSONObject(mHandler.getReuslt());
					if(coverUploadResult.getString("status").equals("success"))
					{
						final String coverServerUrl=coverUploadResult.getString("url");
						mProgressDialog.setMessage("正在上传视频");
						File video=new File(videoPath);
						HttpUriRequest req=NetUtils.makePostRequest(AppConfig.URL.VIDEO_UPLOAD_URL,"video",video);
						UriRequestTask t=new UriRequestTask(req,mHandler);
						t.setRequestCallBack(new UriRequestTask.IRequestCallBack() {
							
							@Override
							public void onSuccess() {
								Log.d("Upload",mHandler.getReuslt());
								try {
									JSONObject videoUploadResult=new JSONObject(mHandler.getReuslt());
									String videoUrl=videoUploadResult.getString("url");
									
									String url=NetUtils.buidUrlWithToken(AppConfig.URL.ADD_VIDEO_URL,AccountUtils.getToken(VideoPublishActivity.this));
									Map<String,String> params=new HashMap<String, String>();
									params.put("video1.url",videoUrl);
									params.put("video1.cover_url", coverServerUrl);
									params.put("video1.tag",tags);
									params.put("video1.desc",desc);
									HttpUriRequest pubRequest=NetUtils.makePostRequest(url,params);
									UriRequestTask t2=new UriRequestTask(pubRequest,mHandler);
									t2.setRequestCallBack(new UriRequestTask.IRequestCallBack()
									{

										@Override
										public void onSuccess() {
											Log.d("------TEST------",mHandler.getReuslt());
											mProgressDialog.dismiss();
											
											Intent intent=new Intent(VideoPublishActivity.this,HomeActivity.class);
											startActivity(intent);
											finish();
										}

										@Override
										public void onFailed() {
											// TODO Auto-generated method stub
											mProgressDialog.dismiss();
										}
										
									});
									t2.excute();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
							}
							
							@Override
							public void onFailed() {
								// TODO Auto-generated method stub
								mProgressDialog.dismiss();
							}
						});
						t.excute();
					}else {
						makeHint(coverUploadResult.getString("message"));
					}
				}catch(JSONException e)
				{
					makeHint("上传图片失败");
				}
	
			}

			@Override
			public void onFailed() {
				mProgressDialog.dismiss();
			}
		});
		
		task.excute();	
	}
}
