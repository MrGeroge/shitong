package net.zypro.istrone.ui;

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
import net.zypro.istrone.util.RegExUtils;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	private ProgressDialog progressdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final EditText usernameET=(EditText)findViewById(R.id.username);
		final EditText passwordET=(EditText)findViewById(R.id.password);
		
		String username=this.getIntent().getStringExtra("username");
		
		if(!TextUtils.isEmpty(username))
		{
			usernameET.setText(username);
		}
		
		String password=this.getIntent().getStringExtra("password");
		
		if(!TextUtils.isEmpty(password))
		{
			passwordET.setText(password);
		}
		
		
		ActionBar actionBar = getActionBar();
		if(actionBar!=null)
		{
			actionBar.show();
			actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.primary));
		}
		
		final Button loginBtn=(Button)findViewById(R.id.login);
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username=usernameET.getText().toString();
				String password=passwordET.getText().toString();
				
				
				if(TextUtils.isEmpty(username))
				{
					makeHint("�˻�����Ϊ��");
					return;
				}
				
				if(TextUtils.isEmpty(password))
				{
					makeHint("���벻��Ϊ��");
					return;
				}
				
				if(!RegExUtils.matchByEmail(username))
				{
					makeHint("�˻���ʽ����");
					return;
				}
				
				if(!RegExUtils.matchByPass(password))
				{
					makeHint("�����ʽ����");
					return;
				}
				
				login(username, password);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent=new Intent(this,RegisterActivity.class);
		startActivity(intent);
		
		return super.onOptionsItemSelected(item);
	}

	private void makeHint(String content)
	{
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}
	
	private void login(final String username,final String password)
	{
		if(AppConfig.isConnect(this))
		{
			progressdialog =ProgressDialog.show(this, "��ȴ�...", "����Ϊ����½...");
			
			Map<String , String> params=new HashMap<String , String>();
			params.put("user.username",username);
			params.put("user.password",password);
			
			final StringHandler mHandler=new StringHandler();
	
			HttpUriRequest request=NetUtils.makePostRequest(AppConfig.URL.LOGIN_URL, params);
			UriRequestTask task=new UriRequestTask(request,mHandler);
		    
			task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {
				
				@Override
				public void onSuccess() {
					progressdialog.cancel();
					
					JSONObject jsonObj;
					try {
						jsonObj = new JSONObject(mHandler.getReuslt());
						
						if(jsonObj.get("status").equals("success"))
						{
							//��¼�ɹ�
							makeHint("��½�ɹ�");
							//��ϸ����д�뱾��
							String token=jsonObj.get("sessionId").toString();
							AccountUtils.saveAccount(password,token,LoginActivity.this);
							AccountUtils.setUsername(LoginActivity.this, username);
							AccountUtils.setNickname(LoginActivity.this,jsonObj.getString("nickName"));
							
							//��ת��������
							Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
							startActivity(intent);
							finish();
							
						}else
						{
							//��¼ʧ��
							makeHint(jsonObj.get("message").toString());
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						makeHint("�������쳣,��½ʧ��");
					}
				}
				
				@Override
				public void onFailed() {
					progressdialog.cancel();
					
					makeHint("�����쳣,��½ʧ��");
				}
			});
			
			task.excute();
		}else {
			makeHint("�������,������������");
		}
	}
}
