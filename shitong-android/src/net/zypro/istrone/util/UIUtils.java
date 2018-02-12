package net.zypro.istrone.util;

public class UIUtils {
	public static int dip2px(float dpValue,float density) {  
        final float scale = density;  
        return (int) (dpValue * scale + 0.5f);  
    } 
}
