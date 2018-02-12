package net.zypro.winter.video.util;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    static
    {
    	//"E:\\tomcat7.0\\apache-tomcat-7.0.42\\webapps\\video0830\\WEB-INF\\classes\\hibernate.cfg.xml"
    	//Configuration.class.getResource("/").getPath()+"hibernate.cfg.xml"
    	//"C:\\Users\\caiqi\\Desktop\\项目\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\videolast\\build\\classes\\"+"hibernate.cfg.xml"
    	Configuration configuration=new Configuration().configure(new File("C:\\Users\\caiqi\\Desktop\\项目\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\新建文件夹\\videolast\\build\\classes\\"+"hibernate.cfg.xml"));	
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());  
    	StandardServiceRegistryImpl registry = (StandardServiceRegistryImpl) builder.build();
        sessionFactory=configuration.buildSessionFactory(registry);
    }
    
    public static SessionFactory getSessionFactory()
    {
    	return sessionFactory;
    }
}