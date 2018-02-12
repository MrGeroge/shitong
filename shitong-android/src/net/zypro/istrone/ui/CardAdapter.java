package net.zypro.istrone.ui;

import java.util.LinkedList;
import java.util.List;

import net.zypro.istrone.model.Video;
import net.zypro.istrone.util.AppConfig;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CardAdapter extends BaseAdapter{
    private Context ctx;
    private List<Video> videos=new LinkedList<Video>();
    
    public void setVideos(List<Video> videos)
    {
    	this.videos=videos;
    	this.notifyDataSetChanged();
    }
    
    public void addVideo(Video video)
    {
    	videos.add(video);
    	this.notifyDataSetChanged();
    }
    
    public void addVideos(List<Video> videos)
    {
    	this.videos.addAll(videos);
    	this.notifyDataSetChanged();
    }
    
    public CardAdapter(Context ctx)
    {
    	this.ctx=ctx;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return videos.size();
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
			convertView=(LinearLayout)LayoutInflater.from(ctx).inflate(R.layout.card, null);
			mViewHolder.commentBtn=(Button)convertView.findViewById(R.id.comment);
			mViewHolder.followBtn=(Button)convertView.findViewById(R.id.follow);
			mViewHolder.heartBtn=(Button)convertView.findViewById(R.id.heart);
			mViewHolder.shareBtn=(Button)convertView.findViewById(R.id.share);
			
			mViewHolder.mPlayer=(PowerVideoPlayer)convertView.findViewById(R.id.powerVideoPlayer);
			mViewHolder.mPlayer.enableDiskCache(AppConfig.ExternalFile.CACHE_PATH);
			
			mViewHolder.nicknameTV=(TextView)convertView.findViewById(R.id.nickname);
			mViewHolder.timeTV=(TextView)convertView.findViewById(R.id.timestamp);
			mViewHolder.descTV=(TextView)convertView.findViewById(R.id.desc);
			mViewHolder.tagBtn=(Button)convertView.findViewById(R.id.tag);
			
			convertView.setTag(mViewHolder);
		}else{
			
			mViewHolder=(ViewHolder)convertView.getTag();
		}
		
		Video video=videos.get(position);
		mViewHolder.nicknameTV.setText(video.nickname);
		mViewHolder.nicknameTV.setTag(video.username);
		mViewHolder.tagBtn.setText(video.tag);
		mViewHolder.descTV.setText(video.desc);
		
		mViewHolder.timeTV.setText(video.time+" ²¥·Å´ÎÊý"+video.viewNumber);
		
		mViewHolder.mPlayer.setVideoCoverUri(video.cover_url);
		mViewHolder.mPlayer.setVideoUri(video.video_url);
        
		return convertView;
	}
	
	class ViewHolder
	{
		public TextView nicknameTV;
		public TextView timeTV;
		public TextView descTV;
		public Button tagBtn;
		public PowerVideoPlayer mPlayer;
		
		public Button followBtn;
		public Button commentBtn;
		public Button heartBtn;
		public Button shareBtn;
	}
}
