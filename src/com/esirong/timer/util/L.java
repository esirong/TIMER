package com.esirong.timer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

/**
 * ����־�ļ�����ģ��ֿɿؿ��ص���־����
 * <P>Ӧ���򷢲�ʱ���뽫��־���ر�Ϊfalse;
 * @author huangrongcai
 */
public class L {
	private static Boolean MYLOG_SWITCH=true; // ��־�ļ��ܿ���
	private static Boolean MYLOG_WRITE_TO_FILE=false;// ��־д���ļ�����
	private static char MYLOG_TYPE='v';// ������־���ͣ�w���ֻ����澯��Ϣ�ȣ�v������������Ϣ
	private static String MYLOG_PATH_SDCARD_DIR=Environment.getExternalStorageDirectory().getAbsolutePath();// ��־�ļ���sdcard�е�·��
	private static int SDCARD_LOG_FILE_SAVE_DAYS = 1;// sd������־�ļ�����ౣ������
	private static String MYLOGFILEName = "Log.txt";// �����������־�ļ����
	private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// ��־�������ʽ
	private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// ��־�ļ���ʽ

	public static void w(String tag, Object msg) { // ������Ϣ
		log(tag, msg.toString(), 'w');
	}

	public static void e(String tag, Object msg) { // ������Ϣ
		log(tag, msg.toString(), 'e');
	}

	public static void d(String tag, Object msg) {// ������Ϣ
		log(tag, msg.toString(), 'd');
	}

	public static void i(String tag, Object msg) {//
		log(tag, msg.toString(), 'i');
	}

	public static void v(String tag, Object msg) {
		log(tag, msg.toString(), 'v');
	}

	public static void w(String tag, String text) {
		log(tag, text, 'w');
	}

	public static void e(String tag, String text) {
		log(tag, text, 'e');
	}

	public static void d(String tag, String text) {
		log(tag, text, 'd');
	}

	public static void i(String tag, String text) {
		log(tag, text, 'i');
	}

	public static void v(String tag, String text) {
		log(tag, text, 'v');
	}

	
	private static void log(String tag, String msg, char level) {
		if (MYLOG_SWITCH) {
			if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // ���������Ϣ
				Log.e(tag, msg);
			} else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
				Log.w(tag, msg);
			} else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
				Log.d(tag, msg);
			} else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
				Log.i(tag, msg);
			} else {
				Log.v(tag, msg);
			}
			if (MYLOG_WRITE_TO_FILE)
				writeLogtoFile(String.valueOf(level), tag, msg);
		}
	}

	/**
	 * ����־�ļ���д����־
	 * 
	 * @return
	 * **/
	private static void writeLogtoFile(String mylogtype, String tag, String text) {// �½������־�ļ�
		Date nowtime = new Date();
		String needWriteFiel = logfile.format(nowtime);
		String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype
				+ "    " + tag + "    " + text;
		File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
				+ MYLOGFILEName);
		if(!writeable()){
			file = new File(Environment.getDataDirectory().getAbsoluteFile(), needWriteFiel
					+ MYLOGFILEName);
		}
		try {
			FileWriter filerWriter = new FileWriter(file, true);//��������������ǲ���Ҫ�����ļ���ԭ������ݣ������и���
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			bufWriter.write(needWriteMessage);
			bufWriter.newLine();
			bufWriter.close();
			filerWriter.close();
		} catch (IOException e) {
			L.e("error", e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���ƶ�����־�ļ�
	 * */
	public static void delFile() {// ɾ����־�ļ�
		String needDelFiel = logfile.format(getDateBefore());
		File file = new File(MYLOG_PATH_SDCARD_DIR, needDelFiel + MYLOGFILEName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * �õ�����ʱ��ǰ�ļ������ڣ������õ���Ҫɾ�����־�ļ���
	 * */
	private static Date getDateBefore() {
		Date nowtime = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowtime);
		now.set(Calendar.DATE, now.get(Calendar.DATE)
				- SDCARD_LOG_FILE_SAVE_DAYS);
		return now.getTime();
	}

	private static boolean writeable(){
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return true;
			}
		else{
			Log.w("error", "sdcardδ׼����");
			return false;
		}
	}
}
