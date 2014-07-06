package com.esirong.timer.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.esirong.timer.model.UpdateInfo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;
import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;



/**
 * �汾��
 * @author esirong
 *
 */
public class AppUpdateUtil{
	private static UpdateInfo updateInfo;
	private Context mcontext;
	
	AppUpdateUtil(Context mcontext){
		this.mcontext = mcontext;
	}
	/**
	 * ��������Ϣ
	 * @param inStream ������
	 * @return
	 * @throws XmlPullParserException
	 */
	public static UpdateInfo getUpdateInfo(InputStream inStream) throws XmlPullParserException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inStream, "UTF-8");
			int type;
            type = parser.getEventType();
            UpdateInfo info = new UpdateInfo();
			while (type != END_DOCUMENT) {
				switch (type) {
				case XmlPullParser.START_TAG:
					if ("path".equals(parser.getName())) {
						info.setPath(parser.nextText());
					} else if ("version".equals(parser.getName())) {
						info.setVersion(Integer.parseInt(parser.nextText()));
					} else if ("description".equals(parser.getName())) {
						info.setDescription(parser.nextText());
					}
					break;
				}
				type = parser.next();
			}

			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡ����Ϣ
	 * @return
	 */
	public static UpdateInfo getUpdateInfo(){
		// String serverUrl = getString(R.string.server_url);
		String serverUrl = "http://10.0.2.2:8080/downloadinfo.xml";
		
		try {
			URL url = new URL(serverUrl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setConnectTimeout(2 * 1000);
	        conn.setReadTimeout(2 * 1000);
	        if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                 return getUpdateInfo(inStream);
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ������
	 * @param mcontext
	 */
	public static void checkForUdpate(final Context mcontext) {
		/*
		 * ��ȡ������Ϣ
		 */
		updateInfo = getUpdateInfo();
		if(updateInfo == null){
			return;
		}
		if (VersionUtil.isNewVersion(updateInfo.getVersion(), mcontext)) {
			AlertDialog.Builder builder = new Builder(mcontext);
			builder.setTitle("�汾��");
//			builder.setIcon(R.id.app_icon);
			builder.setMessage(updateInfo.getDescription() + "\n"
					+ updateInfo.getVersion());
			
			//ȷ����
			builder.setPositiveButton("������", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String downpath = updateInfo.getPath();
					String fileName = DownLoadUtil.getFileName(downpath);
					String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
					String savePath = new String(fileDir + "/" + fileName);
					File file = DownLoadUtil.download(downpath, savePath);
					installApk(file,mcontext);
					
				}
			});
			
			//ȡ��
			builder.setNegativeButton("ȡ��", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}

	}
	
	
	/**
     * 
     * @param file
     */
    protected static void installApk(File file,Context mcontext) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mcontext.startActivity(intent);
    }
}
