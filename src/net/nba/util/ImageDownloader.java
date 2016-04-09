package net.nba.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageDownloader {
	/*
	 * 根据url下载图片存储到本地
	 * 
	 * @param urlStr 图片下载地址
	 * 
	 * @param filePath 图片存储路径
	 * 
	 * @param filename 图片存储名
	 */
	public static void downloadAndSaveImage(String urlStr, String filePath,
			String filename) {
		byte[] img;
		try {
			img = getImageFromNetByUrl(urlStr);
			writeImageToDisk(img, filePath, filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(filename);
		}
		

	}

	/*
	 * 将图片写入到磁盘
	 * 
	 * @param img 图片数据流
	 * 
	 * @param fileName 文件保存时的名称
	 */
	private static void writeImageToDisk(byte[] img, String filePath,
			String fileName) {
		try {
			File fileFolder = new File(filePath);
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			File file = new File(filePath + "/" + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl 网络连接地址
	 * 
	 * @return
	 */
	private static byte[] getImageFromNetByUrl(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
		byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
		return btImg;
	}

	/*
	 * 从输入流中获取数据
	 * 
	 * @param inStream 输入流
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
