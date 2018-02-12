package net.zypro.winter.video.service;

import net.zypro.winter.video.bean.User;
import net.zypro.winter.video.factory.ServiceFactory;

public class FriendManagerTest {
   public static void main(String[] args){
	   FriendManager friendManager=ServiceFactory.getFriendManager();
	   User user=new User(1,"1@test.com","123456");
	   friendManager.AddFriend(user, "3@test.com");
	   
   }
}
