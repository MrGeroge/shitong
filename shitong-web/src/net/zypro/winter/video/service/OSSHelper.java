package net.zypro.winter.video.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;


public class OSSHelper {
	private static final String ACCESS_ID = "zyTvVHAGQSrCYhgv";
    private static final String ACCESS_KEY = "9k2j6KTava4dild1SayQ8o7iJGbrR1";
    private static final String bucketName="zypro-images";
    
    
    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传视频
    public static String uploadVideo(String key,  InputStream input)
            throws OSSException, ClientException {
    	OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
    	
        ObjectMetadata objectMeta = new ObjectMetadata();
        try {
			objectMeta.setContentLength(input.available());
			// 可以在metadata中标记文件类型
	        objectMeta.setContentType("video/mp4");
		    client.putObject(bucketName, key, input, objectMeta);
		    
		    return "http://"+bucketName+".oss-cn-hangzhou.aliyuncs.com/"+key;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }
    public static String uploadImage(String key,  InputStream input)
            throws OSSException, ClientException {
    	OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
    	
        ObjectMetadata objectMeta = new ObjectMetadata();
        try {
			objectMeta.setContentLength(input.available());
			// 可以在metadata中标记文件类型
	        objectMeta.setContentType("image/jpeg");
		    client.putObject(bucketName, key, input, objectMeta);
		    
		    return "http://"+bucketName+".oss-cn-hangzhou.aliyuncs.com/"+key;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }
}
