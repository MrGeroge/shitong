package net.zypro.istrone.ui;

import java.util.LinkedList;
import java.util.List;

import net.zypro.istrone.handler.StringHandler;
import net.zypro.istrone.handler.UriRequestTask;
import net.zypro.istrone.model.Video;
import net.zypro.istrone.util.AppConfig;
import net.zypro.istrone.util.NetUtils;

import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.media.JetPlayer.OnJetEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VideoListFragment extends Fragment{
	private final static int SEARCH_THREAD_FREE=0;
	private final static int SEARCH_THREAD_BUSY=1;
	
	private static int SEARCH_THREAD_STATE=SEARCH_THREAD_FREE;
	
	private int index=0;   //默认起始位置
	private int number=5;  //默认一次最多加载5条
	
	private String url;
	
	private CardAdapter mAdapter;
	private PullToRefreshListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mAdapter=new CardAdapter(this.getActivity());
		
		listView=(PullToRefreshListView)inflater.inflate(R.layout.fragment_video_list,container,false );
		listView.setAdapter(mAdapter);
		
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

 			@Override
 			public void onLastItemVisible() {
 				if(index>mAdapter.getCount())
 				{
 					finchData(buildFinch(url));
 				}else{
 					Toast.makeText(getActivity(), "已经最低", Toast.LENGTH_SHORT).show();
 				}	
 			}
 		});
		
		Bundle bundle=this.getArguments();
		if(bundle!=null){
			String url=bundle.getString("url");
			finchData(buildFinch(url));
		}
		
		return listView;
	}
	
	private String buildFinch(String url)
	{
		return url+"?beginIndex="+String.valueOf(index)+"&count="+String.valueOf(number);
	}
	
	private void finchData(String url)
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
							Log.d("VIDEO_LIST",mHandler.getReuslt());
							
							resultObj=new JSONObject(mHandler.getReuslt());
							
							jsonArr = resultObj.getJSONArray("array");
							
							List<Video> videos=new LinkedList<Video>();
							
							for(int i=0;i!=jsonArr.length();i++)
							{
								JSONObject obj=jsonArr.getJSONObject(i);
								Video video=new Video();
								video.id=obj.getInt("id");
								
								video.time=obj.getString("time");
								video.nickname=obj.getString("nickname");
								video.username=obj.getString("username");
								
								String avatar_url=obj.getString("avatar");
								if(!avatar_url.equals("null"))
									video.avatar_url=avatar_url;
								
								video.cover_url=obj.getString("cover_url");
								video.video_url=obj.getString("video_url");
								video.tag=obj.getString("tag");
								video.desc=obj.getString("desc");
								
								video.viewNumber=obj.getInt("viewNumber");
								video.republishedNumber=obj.getInt("republishedNumber");
								video.commentNumber=obj.getInt("commentNumber");
								video.sharedNumber=obj.getInt("sharedNumber");
								video.lovedNumber=obj.getInt("lovedNumber");
								
								videos.add(video);
							}
							
							if(videos.size()>0)
							{
								mAdapter.addVideos(videos);
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
	
	private void makeHint(String content)
	{
		Toast.makeText(this.getActivity(), content, Toast.LENGTH_SHORT).show();
	}
}
