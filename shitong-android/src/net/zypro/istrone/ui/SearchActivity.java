package net.zypro.istrone.ui;

import java.security.PublicKey;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class SearchActivity extends Activity implements SearchView.OnQueryTextListener,ActionBar.TabListener{


	
	private SearchView mSearchView;
	private FragmentManager fragmentManager=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		
		initActionbar();
		
		mSearchView.setOnQueryTextListener(this);
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
	    View mTitleView = mInflater.inflate(R.layout.action_bar_search, 
	                null); 
	    actionBar.setCustomView( 
	                mTitleView, 
	                new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)); 
	    mSearchView = (SearchView) mTitleView.findViewById(R.id.search_view);
	    
	    ((ImageView)mTitleView.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
			}
		});
	    
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.addTab(actionBar.newTab().setText("”√ªß").setTabListener(this));
	    actionBar.addTab(actionBar.newTab().setText("±Í«©").setTabListener(this));
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		
		
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		
		if(searcher!=null)
		{
			searcher.search(arg0);
		}
		
		return false;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
				
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = null;
		
		searcher=null;
		
		switch (tab.getPosition()) {
			case 0:
				fragment=new UserListFragment();
				searcher=(UserListFragment)fragment;
				break;
			case 1:
				
				break;
		} 
		
		if(fragment!=null)
   	    {
			 if(fragmentManager==null)
				 fragmentManager = getFragmentManager();
			
       	     FragmentTransaction transaction = fragmentManager.beginTransaction();  
             transaction.replace(R.id.main_content, fragment);  
             transaction.commit();  
   	    }
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	private ISearchable searcher;
	
	public interface ISearchable
	{
		 void search(String keywords);
	}
}
