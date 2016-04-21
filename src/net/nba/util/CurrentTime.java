package net.nba.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTime {
	/**
	 * @return	获取当前系统时间
	 */
	public static String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
	
}
