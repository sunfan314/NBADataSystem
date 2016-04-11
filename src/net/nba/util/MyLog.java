package net.nba.util;

import com.sun.jndi.url.dns.dnsURLContext;

/**
 * @author sunfan314
 *在日志文件TestLog中记录日志信息
 */
public class MyLog {
	public static void e(String info) {
		MyFileWriter.appendToFile(info, FilePathManager.LOGPATH);
	}

	public static void e(String key, String value) {
		String str = key + "	:	" + value;
		MyFileWriter.appendToFile(str, FilePathManager.LOGPATH);
	}

	public static void d(String info) {
		MyFileWriter.appendToFile(info, FilePathManager.LOGPATH);
	}

	public static void d(String key, String value) {
		String str = key + "	:	" + value;
		MyFileWriter.appendToFile(str, FilePathManager.LOGPATH);
	}

	public static void i(String info) {
		MyFileWriter.appendToFile(info, FilePathManager.LOGPATH);
	}

	public static void i(String key, String value) {
		String str = key + "	:	" + value;
		MyFileWriter.appendToFile(str, FilePathManager.LOGPATH);
	}

}
