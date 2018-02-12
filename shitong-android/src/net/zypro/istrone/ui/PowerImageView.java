package net.zypro.istrone.ui;

import java.io.File;

import net.zypro.istrone.util.DiskCache;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class PowerImageView extends RelativeLayout{
	
	private DiskCache mDiskCache;
	
	public void enableDiskCache(String baseDir)
	{
		mDiskCache=new DiskCache(Environment.getExternalStorageDirectory().getAbsolutePath());
	}
	
	private ImageView mImageView;
	
	public void setImagePath(String path)
	{
		if(mImageView!=null)
		{
		  if(new File(path).exists())	
			 mImageView.setImageBitmap(BitmapFactory.decodeFile(path));
		}
	}
	
	public void setImageUrl(String url)
	{
		if(mImageView!=null)
		{
			if(mDiskCache!=null)
			{
				mDiskCache.loadFile(url,new DiskCache.ILoadComplete() {
					
					@Override
					public void loadComplete(File target) {
						mImageView.setImageBitmap(BitmapFactory.decodeFile(target.getAbsolutePath()));
						
					}
				});
			}
		}
	}
	
	public PowerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		RelativeLayout.LayoutParams mImageViewParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		
		mImageView=new ImageView(context);
		mImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.person_image_empty));
		this.addView(mImageView,mImageViewParams);
	}
}
