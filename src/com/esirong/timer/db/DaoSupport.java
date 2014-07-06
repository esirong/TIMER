package com.esirong.timer.db;
//package com.esirong.aweek.db;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//import android.content.ContentValues;
//import android.content.Context;
//
//
///**
// * ��ݿ���ʲ���������<br>
// * ��װ����
// * @author esirong
// * @param <M>
// */
//public abstract class DaoSupport<M>{
//	protected DBHelper dbHelper;
//	
//	/**
//	 * 
//	 * @param mContext
//	 */
//	public DaoSupport(Context mContext) {
//		dbHelper = DBHelper.getInstance(mContext);
//		
//		
//	}
//	
//	
//	/**
//	 * ����һ�����
//	 * @param m �����ʵ��bean
//	 * @return �����id
//	 */
//	public long insert(M m) {
//		ContentValues values = new ContentValues();
//		String table = "";
//		String nullColumnHack = "";
//		long id= dbHelper.getWritableDatabase().insert(table, nullColumnHack, values);
//		dbHelper.close();
//		return id;
//	}
//
//	public int delete(Serializable id) {
//		return 0;
//	}
//
//	public int update(M m) {
//		return 0;
//	}
//
//	public ArrayList<M> findAll() {
//		return null;
//	}
//	
//	
//}
