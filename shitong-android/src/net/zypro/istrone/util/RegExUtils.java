package net.zypro.istrone.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegExUtils {
	public static boolean matchByEmail(String str)
	{
		String reg="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	public static boolean matchByPass(String str)
	{
		String reg="^[A-Za-z0-9]+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
}
