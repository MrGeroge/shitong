package net.zypro.istrone.ui;

import net.zypro.istrone.ui.recorder.FFmpegRecorderActivity;
import net.zypro.istrone.util.AppConfig;


import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeActivity extends BaseActivity {
	
	private FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		fragmentManager=getFragmentManager();
		Fragment fragment=new VideoListFragment();
		Bundle args=new Bundle();
		args.putString("url",AppConfig.URL.LIST_VIDEO_URL);
		fragment.setArguments(args);
		if(fragment!=null){	
			FragmentTransaction transaction = fragmentManager.beginTransaction();  
	        transaction.replace(R.id.main_content,fragment);
	        transaction.commit();
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=new MenuInflater(this);
		inflater.inflate(R.menu.home, menu);
		
		Button btn=(Button)menu.findItem(R.id.start_recorder).getActionView();
		btn.setBackgroundResource(R.drawable.start_recorder);
				
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity.this,FFmpegRecorderActivity.class);
				startActivity(intent);
			}
		});
		
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	protected int getSelfNavDrawerItem() {
		return NAVDRAWER_ITEM_HOME ;
	}

	@Override
	protected String getActivityTitle() {
		return "ึ๗าณ";
	}
}
