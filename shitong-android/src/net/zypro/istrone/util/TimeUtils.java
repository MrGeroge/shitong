package net.zypro.istrone.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	public static String getExactTime()
	{
		return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSZ").format(new Date());
	}
}
