package net.zypro.istrone.ui;

import java.io.File;

import android.os.Bundle;
import android.R.anim;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

public class VideoPreviewActivity extends Activity {

	private String videoPath;
	private String coverPath;

	private PowerVideoPlayer player;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_preview);
		
		
		videoPath = getIntent().getStringExtra("videoPath");
		coverPath = getIntent().getStringExtra("imagePath");
		
		PowerVideoPlayer player=(PowerVideoPlayer)findViewById(R.id.powerVideoPlayer1);
		player.setVideoCoverPath(coverPath);
		player.setVideoPath(videoPath);
        
	    ActionBar actionBar=getActionBar();
	    actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.black));
	    actionBar.setLogo(null);
	    actionBar.setTitle("");
	    actionBar.setDisplayHomeAsUpEnabled(true);
  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_preview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();	
			break;

		case R.id.use:
			player=null;
			Intent intent=new Intent(VideoPreviewActivity.this,VideoPublishActivity.class);
			intent.putExtra("coverPath",coverPath);
			intent.putExtra("videoPath", videoPath);
			startActivity(intent);
			finish();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		player=null;
		deleteFile();
		super.onBackPressed();
		finish();
	}

	private void deleteFile()
	{
		File file=new File(videoPath);
		file.delete();
		file=new File(coverPath);
		file.delete();
	}
}
