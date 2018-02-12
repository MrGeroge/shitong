package net.zypro.istrone.ui;

import net.zypro.istrone.ui.R.id;
import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SettingActivity extends BaseActivity implements View.OnClickListener{
	
	private TextView ip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		((Button)findViewById(R.id.logout)).setOnClickListener(this);
		
		
		((LinearLayout)findViewById(R.id.ipInput)).setOnClickListener(this);
		ip=(TextView)findViewById(R.id.ip);
		ip.setText(AppConfig.URL.BASE_URL);
	}

	@Override
	protected int getSelfNavDrawerItem() {
		// TODO Auto-generated method stub
		return NAVDRAWER_ITEM_SETTING;
	}

	@Override
	protected String getActivityTitle() {
		// TODO Auto-generated method stub
		return "设置";
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.logout:
				//清空本地资料
				AccountUtils.cleaAccount(this);
				
				Intent intent=new Intent(this,LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.ipInput:
				LinearLayout editForm=new LinearLayout(this); 
				editForm.setHorizontalGravity(LinearLayout.VERTICAL);
				
				final EditText addrInput=new EditText(this);
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				
				editForm.addView(addrInput, params);
				
				new AlertDialog.Builder(this)
				.setTitle("服务器地址")
				.setView(editForm)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String input=addrInput.getText().toString();
						if(!TextUtils.isEmpty(input))
						{
							AppConfig.URL.BASE_URL=input;
							NetUtils.setServerAddress(SettingActivity.this,AppConfig.URL.BASE_URL);
							AppConfig.URL.reset();
							ip.setText(AppConfig.URL.BASE_URL);
						}
						
						dialog.dismiss();
					}
					
				}).create().show();
				
				break;
		}
		
	}
	
    
}
