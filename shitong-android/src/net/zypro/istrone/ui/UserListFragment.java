package net.zypro.istrone.ui;


import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zypro.istrone.handler.StringHandler;
import net.zypro.istrone.handler.UriRequestTask;
import net.zypro.istrone.util.AccountUtils;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;
import android.R.integer;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserListFragment extends Fragment implements SearchActivity.ISearchable{
	
	
	private final static int SEARCH_THREAD_FREE=0;
	private final static int SEARCH_THREAD_BUSY=1;
	
	private static int SEARCH_THREAD_STATE=SEARCH_THREAD_FREE;
    
	
	private UserAdapter mAdapter;
	
	
	private class User{
		public String username;
		public String nickname;
		public String relation;
		public String avatar_url;
	}
	
	private class UserAdapter extends BaseAdapter
	{
		private List<UserListFragment.User> users=new LinkedList<UserListFragment.User>();
		
		public void setUsers(List<UserListFragment.User> users)
		{
			this.users=users;
			
			this.notifyDataSetChanged();
		}
		
		private Context ctx;
		
		public UserAdapter(Context ctx)
		{
			this.ctx=ctx;
		}
		
		@Override
		public int getCount() {
			return users.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder mViewHolder;
			
			if(convertView==null)
			{
				mViewHolder=new ViewHolder();
			    convertView = (RelativeLayout)LayoutInflater.from(ctx).inflate(R.layout.item_user_list, null);
			    
			    mViewHolder.mImageView=(PowerImageView)convertView.findViewById(R.id.avatar);
			    mViewHolder.mTextView=(TextView)convertView.findViewById(R.id.nickname);
			    mViewHolder.mButton=(Button)convertView.findViewById(R.id.changeBtn);
			    
			    convertView.setTag(mViewHolder);
			}else {
				mViewHolder=(ViewHolder)convertView.getTag();
			}
			
			
			User user=users.get(position);
			
			mViewHolder.mTextView.setText(user.nickname);
			mViewHolder.mButton.setTag(user.username);
			mViewHolder.mButton.setText(getOperation(user.relation));
			mViewHolder.mButton.setOnClickListener(new ClickListener(ctx));
			
			return convertView;
		}
		
		class ClickListener implements Button.OnClickListener{
			private Context mContext;
			
			public ClickListener(Context mContext) {
				super();
				this.mContext = mContext;
			}
		
			@Override
			public void onClick(View v) {
				String url=null;
				final Button btn=(Button)v;
				if(btn.getText().equals("取消关注"))
				{
					url=AppConfig.URL.DELETE_FRIEND_URL;
				}
				
				if(btn.getText().equals("添加关注"))
				{
					url=AppConfig.URL.ADD_FRIEND_URL;
				}
				
				if(btn.getText().equals("相互关注"))
				{
					url=AppConfig.URL.DELETE_FRIEND_URL;
				}
				
				url=NetUtils.buidUrlWithToken(url,AccountUtils.getToken(mContext));
				url+="?fri.username="+btn.getTag().toString().trim();
				
				final ProgressDialog progressdialog=ProgressDialog.show(mContext, "请等待...", "正在执行操作");
				final StringHandler mHandler=new StringHandler();
				
				if(AppConfig.isConnect(mContext))
				{
					HttpUriRequest request=NetUtils.makeGetRequest(url, null);	
					UriRequestTask task=new UriRequestTask(request,mHandler);
					
					task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {

						@Override
						public void onSuccess() {
							progressdialog.dismiss();
							
							try {
								JSONObject obj=new JSONObject(mHandler.getReuslt());
								btn.setText(getOperation(obj.getString("tag")));
								   
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							Log.d("ADD/DELE FRIEND SUCCESS",mHandler.getReuslt());
						}

						@Override
						public void onFailed() {
							progressdialog.dismiss();
							Toast.makeText(mContext,"网络异常,操作异常",Toast.LENGTH_SHORT).show();
							Log.d("ADD/DELE FRIEND FAILED",mHandler.getReuslt());
						}
					});
					
					task.excute();
				}
			}
		}
		
		private String getOperation(String relation)
		{
			if(relation.equals("未关注"))
			{
				return "添加关注";
			}
			
			if(relation.equals("已关注"))
			{
				return "取消关注";
			}
			
			if(relation.equals("相互关注"))
			{
				return "相互关注";
			}
			
			return null;
		}
		
		class ViewHolder
		{
			public PowerImageView mImageView;
			public TextView  mTextView;
			public Button  mButton;
		}
		
	}

	private boolean flag;
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listView=(ListView)inflater.inflate(R.layout.fragment_user_list,container,false );
		
        mAdapter=new UserAdapter(this.getActivity());
		
        listView.setAdapter(mAdapter);
        
        flag=false;
        String url=null;
        
        if(this.getArguments()!=null)
        {
            flag=this.getArguments().getBoolean("flag",false);
            url=this.getArguments().getString("url");
        }
        
        
        
        if(!TextUtils.isEmpty(url))
        	finchData(url);
        
		return listView;
	}
	
	private void makeHint(String content)
	{
		Toast.makeText(this.getActivity(), content, Toast.LENGTH_SHORT).show();
	}
	
	public void finchData(String url)
	{
		if(SEARCH_THREAD_STATE==SEARCH_THREAD_FREE)
		{
			final ProgressDialog progressdialog=ProgressDialog.show(this.getActivity(), "请等待...", "正在获取数据...");
			final StringHandler mHandler=new StringHandler();
			
			if(AppConfig.isConnect(this.getActivity()))
			{
				SEARCH_THREAD_STATE=SEARCH_THREAD_BUSY;
				
				HttpUriRequest request=NetUtils.makeGetRequest(url, null);	
				UriRequestTask task=new UriRequestTask(request,mHandler);
				
				task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {
					
					@Override
					public void onSuccess() {
						progressdialog.cancel();
						
						JSONObject resultObj;
						JSONArray jsonArr;
						try {
							Log.d("USER_LIST",mHandler.getReuslt());
							
							resultObj=new JSONObject(mHandler.getReuslt());
							
							jsonArr = resultObj.getJSONArray("arr");
							
							List<User> users=new LinkedList<UserListFragment.User>();
							for(int i=0;i!=jsonArr.length();i++)
							{
								JSONObject obj=jsonArr.getJSONObject(i);
								
								User user=new User();
								user.username=obj.getString("username");
								user.nickname=obj.getString("nickname");
								user.relation=obj.getString("tag");
								
								
								users.add(user);
							}
							
							if(users.size()==0){
								makeHint("对不起,未找到相关用户");
							}else {
								mAdapter.setUsers(users);
								updateUI();
							}
							
						} catch (JSONException e) {
							makeHint("服务器异常,获取失败");
						}
						
						SEARCH_THREAD_STATE=SEARCH_THREAD_FREE;
					}
					
					@Override
					public void onFailed() {
						makeHint("服务器异常,获取失败");
						progressdialog.cancel();
						SEARCH_THREAD_STATE=SEARCH_THREAD_FREE;
					}
				});
				
				task.excute();
			}else {
				makeHint("网络异常,获取失败");
			}
		}
	}
	
	private void updateUI()
	{
		if(flag)
        {
		        ListAdapter listAdapter = listView.getAdapter();  
		        int totalHeight = 0; 
	
		        for (int i = 0; i < listAdapter.getCount(); i++) {
		            View listItem = listAdapter.getView(i, null, listView); 
		            listItem.measure(0, 0); 
		            totalHeight += listItem.getMeasuredHeight(); 
		        } 
		
		        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
		
		        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
		
		        listView.setLayoutParams(params); 
        }
		
	}

	@Override
	public void search(String keywords) {
		String url=NetUtils.buidUrlWithToken(AppConfig.URL.FIND_FRIEND_URL,AccountUtils.getToken(this.getActivity()));
		url+="?fri.username="+keywords.trim();
		finchData(url);
	}
}
