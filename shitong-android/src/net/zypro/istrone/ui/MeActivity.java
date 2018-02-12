package net.zypro.istrone.ui;


import net.zypro.istrone.ui.R.id;
import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;
import net.zypro.istrone.util.UIUtils;
import android.os.Bundle;
import android.R.integer;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SpinnerAdapter;

public class MeActivity extends BaseActivity implements MyScrollView.OnScrollListener{

	private RadioGroup mNavTab;
	private static final int MY_VIDEO_TAB=R.id.me_nav_tab_item_my;
	private static final int MY_FOLLOWING_TAB=R.id.me_nav_tab_item_following;
	private static final int MY_FOLLOWER_TAB=R.id.me_nav_tab_item_follower;
	
	private FragmentManager fragmentManager;
	
	
	private MyScrollView mScrollView;  
    private LinearLayout mBottomLayout;
    
    
    //ScrollerView可以向下滚动的最大高度
    private int maxScrollerTopDis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me);
		
		//获取屏幕高度
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        
        //设置Scroller滚动的最大距离
        maxScrollerTopDis=UIUtils.dip2px(180, metric.density);

        
        fragmentManager=getFragmentManager();
        
		mNavTab=(RadioGroup)findViewById(R.id.me_nav_tab);
				
		//监听NavTab
		mNavTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
	            @Override  
	            public void onCheckedChanged(RadioGroup group, int checkedId) { 
	            	setupMainBody(checkedId);
	            }
		});
		
		
		
		mNavTab.check(MY_VIDEO_TAB);
		
		mScrollView=(MyScrollView)findViewById(R.id.main_content);
		//mScrollView.setOnScrollListener(this);
		
		//mBottomLayout=(LinearLayout)findViewById(R.id.bottom_content);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.me, menu);
		return true;
	}
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    int id = item.getItemId();
	    
	    switch (id) {
			case R.id.settings:
				Intent intent=new Intent(this,EditInfoActivity.class);
				startActivity(intent);
				break;
		}
	    
	    return super.onOptionsItemSelected(item);
	 }

	@Override
	protected int getSelfNavDrawerItem() {
		return NAVDRAWER_ITEM_USER;
	}

	@Override
	protected String getActivityTitle() {
		return "个人中心";
	}

	private void setupMainBody(int checkedId) {
		Fragment fragment=null;
        String url;
		Bundle bundle;
		
		switch (checkedId) {
			case MY_VIDEO_TAB:
				
				break;
			case MY_FOLLOWING_TAB:
				url=NetUtils.buidUrlWithToken(AppConfig.URL.GET_FOLLOWING_URL,AccountUtils.getToken(this));
				
				fragment=new UserListFragment();
				bundle=new Bundle();
	            bundle.putBoolean("flag", true);
	            bundle.putString("url", url);
	            fragment.setArguments(bundle);
				
	            /*
	            Fragment followingFragment1=new UserListFragment();
	            bundle=new Bundle();
	            bundle.putString("url", url);
	            followingFragment1.setArguments(bundle);
	            
	            FragmentTransaction transaction1 = fragmentManager.beginTransaction();  
	            transaction1.replace(R.id.list_content_bottom, followingFragment1);
	            transaction1.commit();*/
	            
				break;
			case MY_FOLLOWER_TAB:
				url=NetUtils.buidUrlWithToken(AppConfig.URL.GET_FOLLOWER_URL,AccountUtils.getToken(this));
				
				fragment=new UserListFragment();
				bundle=new Bundle();
	            bundle.putBoolean("flag", true);
	            bundle.putString("url", url);
	            fragment.setArguments(bundle);
				break;
		}
		
		 if(fragment!=null){	
			FragmentTransaction transaction = fragmentManager.beginTransaction();  
	        transaction.replace(R.id.list_content,fragment);
	        transaction.commit();
		 }
	}

	@Override
	public void onScroll(int scrollY) {
		if(scrollY >= maxScrollerTopDis){   
                showBottom();  
        }
	}

	private void removeBottom() {
		
	}

	private void showBottom() {
		mScrollView.setVisibility(View.GONE);
		mBottomLayout.setVisibility(View.VISIBLE);
	}
}
