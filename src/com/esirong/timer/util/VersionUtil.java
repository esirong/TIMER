package com.esirong.timer.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 版本管理
 */

public class VersionUtil {
	/**
	 * 包名
	 */
	private final static String PACKAGE_NAME = "";

	/**
	 * 当前版本号
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
	 * 当前版本名
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
	 * 判断是不是有新版本
	 * @param versionName
	 * @param currentVersionName
	 * @return 
	 */
	public static boolean isNewVersion(String versionName,String currentVersionName) {
		return versionName.compareToIgnoreCase(currentVersionName) > 0;

	}
	/**
	 * 判断是不是有新版本
	 * @param versionName
	 * @param currentVersionName
	 * @return 
	 */
	public static boolean isNewVersion(String versionName,Context context) {
		return versionName.compareToIgnoreCase(getCurrentVersionName(context)) > 0;
		
	}

	/**
	 * 是不是有新版本
	 * @param versionCode
	 * @param currentVersionCode
	 * @return 
	 */
	public static boolean isNewVersion(int versionCode, int currentVersionCode) {
		return versionCode > currentVersionCode;

	}
	
	/**
	 * 是不是有新版本
	 * @param versionCode
	 * @param currentVersionCode
	 * @return 
	 */
	public static boolean isNewVersion(int versionCode,Context context) {
		return versionCode > getCurrentVersionCode(context);
		
	}

}
