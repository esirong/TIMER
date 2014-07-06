package com.esirong.timer.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 *�汾����
 *  
 * 
 */

public class VersionUtil {
	/**
	 * ����
	 */
	private final static String PACKAGE_NAME = "";

	/**
	 * ��ȡ�� ����
	 * 
	 * @param context
	 * @return
	 */
	public static int getCurrentVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	/**
	 * �汾�Ƚ�
	 * @param versionName
	 * @param currentVersionName
	 * @return �°汾true���ɰ汾false
	 */
	public static boolean isNewVersion(String versionName,String currentVersionName) {
		return versionName.compareToIgnoreCase(currentVersionName) > 0;

	}
	/**
	 * �汾�Ƚ�
	 * @param versionName
	 * @param currentVersionName
	 * @return �°汾true���ɰ汾false
	 */
	public static boolean isNewVersion(String versionName,Context context) {
		return versionName.compareToIgnoreCase(getCurrentVersionName(context)) > 0;
		
	}

	/**
	 * �汾�Ƚ�
	 * @param versionCode
	 * @param currentVersionCode
	 * @return �°汾true���ɰ汾false
	 */
	public static boolean isNewVersion(int versionCode, int currentVersionCode) {
		return versionCode > currentVersionCode;

	}
	
	/**
	 * �汾�Ƚ�
	 * @param versionCode
	 * @param currentVersionCode
	 * @return �°汾true���ɰ汾false
	 */
	public static boolean isNewVersion(int versionCode,Context context) {
		return versionCode > getCurrentVersionCode(context);
		
	}

}
