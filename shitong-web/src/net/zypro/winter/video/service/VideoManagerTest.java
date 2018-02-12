package net.zypro.winter.video.service;

import java.util.List;

import net.zypro.winter.video.bean.Video;
import net.zypro.winter.video.factory.ServiceFactory;
import net.zypro.winter.video.util.PageUtil;
import junit.framework.TestCase;

public class VideoManagerTest extends TestCase {

	public void testFindAllVideos() {
		
		VideoManager vm=ServiceFactory.getVideoManager();
		PageUtil page=new PageUtil();
		page.setBeginIndex(0);
		page.setEveryPage(3);
		List<Video> videos=vm.findAllVideos(page);
		for(Video video:videos){
			System.out.println(video.getUser().getUsername());
		}
		
		
		
	}

}
