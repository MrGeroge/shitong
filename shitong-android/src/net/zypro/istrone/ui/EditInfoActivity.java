package net.zypro.istrone.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;

import net.zypro.istrone.handler.StringHandler;
import net.zypro.istrone.handler.UriRequestTask;
import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.DiskCache;
import net.zypro.istrone.util.NetUtils;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class EditInfoActivity extends Activity implements View.OnClickListener{
	
	
	private String NICKNAME_PLACEHOLDER;
	private String SEX_PLACEHOLDER;
	private String ADDRESS_PLACEHOLDER;
	private String DESC_PLACEHOLDER;
	
	private String avatarUrl;
	private TextView nickname;
	private TextView sex;
	private TextView address;
	private TextView desc;
	private DiskCache mDiskCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_info);

		initActionbar();
		
		NICKNAME_PLACEHOLDER=getResources().getString(R.string.nickname_placeholder);
		SEX_PLACEHOLDER=getResources().getString(R.string.sex_placeholder);
		ADDRESS_PLACEHOLDER=getResources().getString(R.string.address_placeholder);
		DESC_PLACEHOLDER=getResources().getString(R.string.desc_placeholder);
		
		mDiskCache=new DiskCache(AppConfig.ExternalFile.CACHE_PATH);
		
		nickname=(TextView)findViewById(R.id.nickname);
		
		String str=AccountUtils.getNickname(this);
		if(!TextUtils.isEmpty(str))
			nickname.setText(str);
		
		sex=(TextView)findViewById(R.id.sex);
		
		str=AccountUtils.getSex(this);
		if(!TextUtils.isEmpty(str))
			sex.setText(str);
			
		address=(TextView)findViewById(R.id.address);
		
		str=AccountUtils.getAddress(this);
		if(!TextUtils.isEmpty(str))
			address.setText(str);
		
		desc=(TextView)findViewById(R.id.desc);
		
		str=AccountUtils.getDesc(this);
		if(!TextUtils.isEmpty(str))
			desc.setText(str);
		
		((ImageView)findViewById(R.id.avatar)).setOnClickListener(this);
		((LinearLayout)findViewById(R.id.nicknameInput)).setOnClickListener(this);
		((LinearLayout)findViewById(R.id.sexInput)).setOnClickListener(this);
		((LinearLayout)findViewById(R.id.addressInput)).setOnClickListener(this);
		((LinearLayout)findViewById(R.id.descInput)).setOnClickListener(this);
	}
	
	public void initActionbar() { 
		ActionBar actionBar=getActionBar();
		actionBar.setIcon(null);
		actionBar.setTitle("");
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.primary));
		actionBar.setDisplayShowHomeEnabled(false); 
		actionBar.setDisplayShowTitleEnabled(false); 
		actionBar.setDisplayShowCustomEnabled(true); 
	    LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	    View mTitleView = mInflater.inflate(R.layout.action_bar_common, 
	                null);
	    
	    
	    actionBar.setCustomView( 
	                mTitleView, 
	                new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)); 
	    
	    ((ImageView)mTitleView.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=new MenuInflater(this);
		inflater.inflate(R.menu.edit_info, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		saveData();
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.avatar:
				editAvatar();
				break;
			case R.id.nicknameInput:
				editNickname();
				break;
			case R.id.sexInput:
				editSex();
				break;
			case R.id.addressInput:
				editAddress();
				break;
			case R.id.descInput:
				editDesc();
				break;
		}
	}
	
	
	//修改头像
	private void editAvatar()
	{
		String[] items=new String[]{"拍照","从相册选取"};
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this)
							.setTitle("图像来源")
							.setItems(items,new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									/*
									  File file=new File(AppConfig.ExternalFile.APP_SDCARD_ROOT+"test.jpg");
	        final StringHandler mHandler=new StringHandler();
	        HttpUriRequest request=NetUtils.makePostRequest(AppConfig.URL.IMAGE_UPLOAD_URL,"image",file);	
			UriRequestTask task=new UriRequestTask(request,mHandler);
	        
			task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {

				@Override
				public void onSuccess() {
					Log.d("Upload",mHandler.getReuslt());
				}

				@Override
				public void onFailed() {
					
				}
			});
			
			task.excute();
									 * */
									dialog.dismiss();
								}
							});
		
		builder.create().show();
	}
	
	private void editSex()
	{
		final String[] items=new String[]{"男","女","保密"};
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this)
							.setTitle("性别")
							.setSingleChoiceItems(items,2,new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									sex.setText(items[which]);
									
									dialog.dismiss();
								}
							});
		
		builder.create().show();
	}
	
	//修改昵称
	private void editNickname()
	{
		LinearLayout editForm=new LinearLayout(this); 
		editForm.setHorizontalGravity(LinearLayout.VERTICAL);
		
		final EditText nameInput=new EditText(this);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		
		editForm.addView(nameInput, params);
		
		new AlertDialog.Builder(this)
		.setTitle("编辑框")
		.setView(editForm)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String input=nameInput.getText().toString();
				if(!TextUtils.isEmpty(input))
					nickname.setText(input);
				
				dialog.dismiss();
			}
			
		}).create().show();
	}
	
	private void editAddress()
	{
		LinearLayout editForm=new LinearLayout(this); 
		editForm.setHorizontalGravity(LinearLayout.VERTICAL);
		
		final EditText nameInput=new EditText(this);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		
		editForm.addView(nameInput, params);
		
		new AlertDialog.Builder(this)
		.setTitle("编辑框")
		.setView(editForm)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String input=nameInput.getText().toString();
				if(!TextUtils.isEmpty(input))
					address.setText(input);
				
				dialog.dismiss();
			}
			
		}).create().show();
	}
	
	private void editDesc()
	{
		LinearLayout editForm=new LinearLayout(this); 
		editForm.setHorizontalGravity(LinearLayout.VERTICAL);
		
		final EditText nameInput=new EditText(this);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		
		editForm.addView(nameInput, params);
		
		new AlertDialog.Builder(this)
		.setTitle("编辑框")
		.setView(editForm)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String input=nameInput.getText().toString();
				if(!TextUtils.isEmpty(input))
					desc.setText(input);
				
				dialog.dismiss();
			}
			
		}).create().show();
	}
	
	
	
	
	private void saveData()
	{
		Map<String,String> params=new HashMap<String, String>();
		if(!TextUtils.isEmpty(avatarUrl))
			params.put("userInfo.avatar","avatarUrl");
		
		if(!nickname.getText().toString().equals(NICKNAME_PLACEHOLDER))
			params.put("userInfo.nickName",nickname.getText().toString());
		
		
		params.put("userInfo.sex",sex.getText().toString());
		params.put("userInfo.desc",desc.getText().toString());
		
		
		final ProgressDialog progressdialog=ProgressDialog.show(this, "请等待...", "正在更新信息...");
		final StringHandler mHandler=new StringHandler();
		
		if(AppConfig.isConnect(this))
		{
			HttpUriRequest request=NetUtils.makeGetRequest(NetUtils.buidUrlWithToken(AppConfig.URL.UPDATA_INFO_URL, AccountUtils.getToken(this)), params);
			UriRequestTask task=new UriRequestTask(request,mHandler);
			
			task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					progressdialog.dismiss();
				}

				@Override
				public void onFailed() {
					// TODO Auto-generated method stub
					progressdialog.dismiss();
				}
				
			});
			
			task.excute();
		}
	}
}
