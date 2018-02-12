package net.zypro.winter.video.service;

import net.zypro.winter.video.factory.ServiceFactory;
import junit.framework.TestCase;

public class UserManagerTest extends TestCase {

	public void testUpdateActive() {
		UserManager userManager=ServiceFactory.getUserManager();
		
		userManager.updateActive("caiqi");
	}

}
