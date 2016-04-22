package net.nba.util;

import com.sun.jndi.url.dns.dnsURLContext;

/**
 * @author sunfan314
 *在日志文件TestLog中记录日志信息
 */
public class MyLog {
	public static void e(String info) {
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+info, FilePathManager.LOGPATH);
	}

	public static void e(String key, String value) {
		String str = key + "	:	" + value;
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+str, FilePathManager.LOGPATH);
	}

	public static void d(String info) {
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+info, FilePathManager.LOGPATH);
	}

	public static void d(String key, String value) {
		String str = key + "	:	" + value;
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+str, FilePathManager.LOGPATH);
	}

	public static void i(String info) {
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+info, FilePathManager.LOGPATH);
	}

	public static void i(String key, String value) {
		String str = key + "	:	" + value;
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+str, FilePathManager.LOGPATH);
	}
	
	public static void log(String info){
		String timeStr=CurrentTime.getCurrentTime()+" : ";
		MyFileWriter.appendToFile(timeStr+info, FilePathManager.RUNTIMELOG);
	}
	

}
