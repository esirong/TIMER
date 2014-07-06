package com.esirong.timer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ���ع���
 * @author esirong
 *
 */
public class DownLoadUtil {
	/**
	 * 
	 * @param serverFilePath
	 * @param savedFilePath
	 * @return
	 */
	public static File download(String serverFilePath, String savedFilePath) {
		try {
			
			URL url = new URL(serverFilePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);

			if (200 == conn.getResponseCode()) {
				InputStream inStream = conn.getInputStream();
				File file = new File(savedFilePath);
				FileOutputStream outStream = new FileOutputStream(file);
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				outStream.close();
				inStream.close();
				return file;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	};
	
	/**
	 * 
	 * @param wholePath
	 * @return
	 */
	public static String getFileName(String wholePath){
		int index = wholePath.lastIndexOf("/");
		return wholePath.substring(index+1);
	}
}
