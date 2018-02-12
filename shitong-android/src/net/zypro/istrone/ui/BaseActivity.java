package net.zypro.istrone.ui;

import java.util.ArrayList;

import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	
	protected static final int NAVDRAWER_ITEM_HOME = 0;
	protected static final int NAVDRAWER_ITEM_EXPLORE = 1;
	protected static final int NAVDRAWER_ITEM_SEARCH = 2;
	protected static final int NAVDRAWER_ITEM_DRAFT = 3;
	protected static final int NAVDRAWER_ITEM_USER = 4;
	protected static final int NAVDRAWER_ITEM_SETTING= 5;
	
    protected static final int NAVDRAWER_ITEM_INVALID = -1;
    protected static final int NAVDRAWER_ITEM_SEPARATOR = -2;
    
	private static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
		R.string.navdrawer_item_home,
		R.string.navdrawer_item_explore,
		R.string.navdrawer_item_search,
		R.string.navdrawer_item_draft,
		R.string.navdrawer_item_user,
		R.string.navdrawer_item_setting
	};
	
	private static final int[] NAVDRAWER_ICON_RES_ID = new int[] {
	    R.drawable.ic_drawer_home,
	    R.drawable.ic_drawer_explore,
	    R.drawable.ic_drawer_search,
	    R.drawable.ic_drawer_draft,
	    R.drawable.ic_drawer_user,
	    R.drawable.ic_drawer_settings
    };
	
	private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;
	
	private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();
	
	private View[] mNavDrawerItemViews = null;
	
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ViewGroup mDrawerItemsListContainer;
	private Handler mHandler;
	
	protected abstract int getSelfNavDrawerItem();
	protected abstract String getActivityTitle();
	
	private ActionBar actionBar=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置服务器地址
		String addr=NetUtils.getServerAddr(this);
		if (!TextUtils.isEmpty(addr)) {
			AppConfig.URL.BASE_URL=addr;
		}
		
		//初始运行环境
		new AppConfig();
		
		mHandler = new Handler();
		
		actionBar = getActionBar();
		if(actionBar!=null)
		{
			//设置actionBar背景色
			actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.primary));
	 
		}
	}
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	        int id = item.getItemId();
	        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        
	    return super.onOptionsItemSelected(item);
	 }
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        
        setupAccount();
        setupNavDrawer();

        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        } else {
            //LOGW(TAG, "No view with ID main_content to fade in.");
        }
    }

    private void setupAccount() {
		if(AccountUtils.hasLocalAccount(this))
		{
			String username=AccountUtils.getUsername(this);
			if(!TextUtils.isEmpty(username))
			    ((TextView)findViewById(R.id.profile_username_text)).setText(username);
			
		}else {
		    Intent intent=new Intent(this,LoginActivity.class);
		    startActivity(intent);  
			finish();
		}
	}
    
    
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }
	
	private void setupNavDrawer() {
		int selfItem = getSelfNavDrawerItem();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }
        if (selfItem == NAVDRAWER_ITEM_INVALID) {
            // do not show a nav drawer
            View navDrawer = mDrawerLayout.findViewById(R.id.navdrawer);
            if (navDrawer != null) {
                ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
            }
            mDrawerLayout = null;
            return;
        }
             
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.drawer_drag, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(getActivityTitle());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        
        populateNavDrawer();
        
        mDrawerToggle.syncState();
	}
	
	private void populateNavDrawer() {
		mNavDrawerItems.clear();
		
		mNavDrawerItems.add(NAVDRAWER_ITEM_HOME);
		mNavDrawerItems.add(NAVDRAWER_ITEM_EXPLORE);
		mNavDrawerItems.add(NAVDRAWER_ITEM_SEARCH);
		
		mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR);
		
		mNavDrawerItems.add(NAVDRAWER_ITEM_DRAFT);
		mNavDrawerItems.add(NAVDRAWER_ITEM_USER);
		mNavDrawerItems.add(NAVDRAWER_ITEM_SETTING);

		
		createNavDrawerItems();
	}
	
	private void createNavDrawerItems() {
        mDrawerItemsListContainer = (ViewGroup) findViewById(R.id.navdrawer_items_list);
        if (mDrawerItemsListContainer == null) {
            return;
        }

        mNavDrawerItemViews = new View[mNavDrawerItems.size()];
        mDrawerItemsListContainer.removeAllViews();
        int i = 0;
        for (int itemId : mNavDrawerItems) {
            mNavDrawerItemViews[i] = makeNavDrawerItem(itemId, mDrawerItemsListContainer);
            mDrawerItemsListContainer.addView(mNavDrawerItemViews[i]);
            ++i;
        }
    }
	
	
	private View makeNavDrawerItem(final int itemId, ViewGroup container) {
        boolean selected = getSelfNavDrawerItem() == itemId;
        int layoutToInflate = 0;
        if (itemId == NAVDRAWER_ITEM_SEPARATOR) {
            layoutToInflate = R.layout.navdrawer_separator;
        }  else {
            layoutToInflate = R.layout.navdrawer_item;
        }
        
        View view = getLayoutInflater().inflate(layoutToInflate, container, false);

        if (isSeparator(itemId)) {
            return view;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        int iconId = itemId >= 0 && itemId < NAVDRAWER_ICON_RES_ID.length ?
                NAVDRAWER_ICON_RES_ID[itemId] : 0;
        int titleId = itemId >= 0 && itemId < NAVDRAWER_TITLE_RES_ID.length ?
                NAVDRAWER_TITLE_RES_ID[itemId] : 0;

        // set icon and text
        iconView.setVisibility(iconId > 0 ? View.VISIBLE : View.GONE);
        if (iconId > 0) {
            iconView.setImageResource(iconId);
        }
        titleView.setText(getString(titleId));

        formatNavDrawerItem(view, itemId, selected);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavDrawerItemClicked(itemId);
            }
        });

        return view;
    }
	
	private void formatNavDrawerItem(View view, int itemId, boolean selected) {
        if (isSeparator(itemId)) {
            // not applicable
            return;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);

        // configure its appearance according to whether or not it's selected
        titleView.setTextColor(selected ?
                getResources().getColor(R.color.navdrawer_text_color_selected) :
                getResources().getColor(R.color.navdrawer_text_color));
        iconView.setColorFilter(selected ?
                getResources().getColor(R.color.navdrawer_icon_tint_selected) :
                getResources().getColor(R.color.navdrawer_icon_tint));
    }
	
	private boolean isSeparator(int itemId) {
        return itemId == NAVDRAWER_ITEM_SEPARATOR;
    }
	
	private void onNavDrawerItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        
        // launch the target Activity after a short delay, to allow the close animation to play
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNavDrawerItem(itemId);
            }
        }, NAVDRAWER_LAUNCH_DELAY);

        // change the active item on the list so the user can see the item changed
        setSelectedNavDrawerItem(itemId);
        // fade out the main content
        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }
     

        mDrawerLayout.closeDrawer(Gravity.START);
    }
	
	private void setSelectedNavDrawerItem(int itemId) {
        if (mNavDrawerItemViews != null) {
            for (int i = 0; i < mNavDrawerItemViews.length; i++) {
                if (i < mNavDrawerItems.size()) {
                    int thisItemId = mNavDrawerItems.get(i);
                    formatNavDrawerItem(mNavDrawerItemViews[i], thisItemId, itemId == thisItemId);
                }
            }
        }
    }
	
	private void goToNavDrawerItem(int item) {
        Intent intent;
        switch (item) {
            case NAVDRAWER_ITEM_HOME :
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_SEARCH:
            	intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                finish();
                break;
                
            case NAVDRAWER_ITEM_USER:
            	intent = new Intent(this, MeActivity.class);
                startActivity(intent);
                finish();
                break;
            case NAVDRAWER_ITEM_SETTING:
            	intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
        }
	}
}
