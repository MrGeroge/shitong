package net.zypro.istrone.ui;

import java.io.File;

import net.zypro.istrone.util.DiskCache;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class PowerVideoPlayer extends RelativeLayout{

	private static final int STOP=0;
	private static final int PLAY=1;
	private static final int PAUSE=2;
	
	private int currentPlayerStatus=STOP;
	
	
	
	public PowerVideoPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext=context;
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		android.view.ViewGroup.LayoutParams layoutParams =new RelativeLayout.LayoutParams(displaymetrics.widthPixels,
				 displaymetrics.widthPixels);
		
		this.setLayoutParams(layoutParams);
		
		RelativeLayout.LayoutParams mVideoCoverParams=new RelativeLayout.LayoutParams(displaymetrics.widthPixels,
				displaymetrics.widthPixels);
		
		mVideoCover=new ImageView(context);
		mVideoCover.setScaleType(ImageView.ScaleType.FIT_XY);
		mVideoCover.setImageDrawable(context.getResources().getDrawable(R.drawable.videoview_default_pic));
		
		this.addView(mVideoCover,mVideoCoverParams);
		
		mVideoView=new VideoView(context);
		mVideoView.setVisibility(View.GONE);
		mVideoView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(currentPlayerStatus)
				{
				case PLAY:
					pause();	
					break;
				}
				
				return true;
			}
		});
		
		this.addView(mVideoView,mVideoCoverParams);
		
		
		RelativeLayout.LayoutParams mVideoPlayBtnParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
		mVideoPlayBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		mVideoPlayBtnParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		
		mVideoPlayBtn=new ImageView(context);
		mVideoPlayBtn.setScaleType(ImageView.ScaleType.FIT_XY);
		mVideoPlayBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.icn_play_big));
		mVideoPlayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				switch(currentPlayerStatus)
				{
				case STOP:
					start();	
					break;
				case PAUSE:
					restart();
					break;
				}
				
			}
		});
		
		
		this.addView(mVideoPlayBtn,mVideoPlayBtnParams);
		
		

		mProgressBar=new ProgressBar(context);
		mProgressBar.setVisibility(View.GONE);
		
		this.addView(mProgressBar,mVideoPlayBtnParams);
	}
	
	public void enableDiskCache(String baseDir)
	{
		mDiskCache=new DiskCache(baseDir);
	}

	private static final String TAG="PowerVideoPlayer";
		
	private String videoUri;
    private String videoPath;
	private DiskCache mDiskCache;
	
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public void setVideoUri(String videoUri) {
		this.videoUri = videoUri;	
	}

	public void setVideoCoverUri(String videoCoverUri) {
		
		if(mDiskCache!=null)
		{
			mDiskCache.loadFile(videoCoverUri,new DiskCache.ILoadComplete() {
				
				@Override
				public void loadComplete(File target) {
					mVideoCover.setImageBitmap(BitmapFactory.decodeFile(target.getAbsolutePath()));	
				}
			});
		}
	}
	
	public void setVideoCoverPath(String videoCoverPath)
	{
	   if(new File(videoCoverPath).exists())	
		     mVideoCover.setImageBitmap(BitmapFactory.decodeFile(videoCoverPath));	
	}
		
	private Context mContext;
	private VideoView mVideoView;
	private ImageView mVideoCover;
	private ImageView mVideoPlayBtn;
	private ProgressBar mProgressBar;
		
	public void start()
	{
		mVideoPlayBtn.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
		
		if(mDiskCache!=null)
		{
			mDiskCache.loadFile(videoUri,new DiskCache.ILoadComplete() {
				
				@Override
				public void loadComplete(File target) {
					start(target.getAbsolutePath());
				}
			});
		}else {
			start(videoPath);
		}
	}
	
	
	private void start(String path)
	{
		mVideoView.setVideoPath(path);
		mVideoCover.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
		mVideoView.setVisibility(View.VISIBLE);
		currentPlayerStatus=PLAY;
		mVideoView.start();
		mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
			  
            @Override  
            public void onCompletion(MediaPlayer mp) {   
                mVideoView.start();  
            }  
        }); 
	}
	
	public void pause()
	{
		currentPlayerStatus=PAUSE;
		mVideoPlayBtn.setVisibility(View.VISIBLE);
		mVideoView.pause();
	}
	
	public void restart()
	{
		currentPlayerStatus=PLAY;
		mVideoPlayBtn.setVisibility(View.GONE);
		mVideoView.start();
	}
	
	public void stop()
	{
		
	}
}
