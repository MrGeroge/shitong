package net.zypro.istrone.util;

import java.io.File;

import net.zypro.istrone.handler.FileHandler;
import net.zypro.istrone.handler.UriRequestTask;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.net.Uri;
import android.util.Log;

public class DiskCache {
	private static final String TAG="DiskCache";
	
    private File cacheDir;  //�����ļ���
	
	public DiskCache(String path)
	{
		File dir=new File(path);
		
		if(dir.isDirectory())
		{
			if(!dir.exists())
				dir.mkdirs();
			
			cacheDir=dir;
		}else
		{
			Log.e(TAG,"Value Is Not Directory");
		}
	}
	
	public interface ILoadComplete{
		void loadComplete(File target);
	}
		
	public void loadFile(String targetURL,final ILoadComplete loadComplete){
		//�����ļ���׺��
		String fileName=Uri.parse(targetURL).getLastPathSegment();
		
		String suffix= fileName.substring(fileName.lastIndexOf(".")+1);
		
		String id=MD5Util.MD5(targetURL)+"."+suffix;
		final File file=new File(cacheDir,id);
		
		if(!file.exists())
		{
			//δ����
			FileHandler mhandler=new FileHandler(cacheDir,id);
			HttpUriRequest mRes=new HttpGet(targetURL);
			UriRequestTask task=new UriRequestTask(mRes,mhandler);
			task.setRequestCallBack(new UriRequestTask.IRequestCallBack() {
				
				@Override
				public void onSuccess() {
					if(loadComplete!=null)
						loadComplete.loadComplete(file);
				}
				
				@Override
				public void onFailed() {
					Log.e(TAG,"DownLoad File Failed");
				}
			});
			
			task.excute();
		}else
		{
			loadComplete.loadComplete(file);
		}
	}
}
