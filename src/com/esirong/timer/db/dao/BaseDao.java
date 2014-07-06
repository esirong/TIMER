package com.esirong.timer.db.dao;
//package com.esirong.aweek.db.dao;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.esirong.aweek.db.DBHelper;
//import com.esirong.aweek.db.dao.annotation.Column;
//
//public class BaseDao<M> {
//
//	private static BaseDao instance;
//	private onResultListener lResultListener;
//	private static DBHelper dbHelper;
//
//	/** ����ݲ��� */
//	public static BaseDao getInstance(Context context) {
//		dbHelper = DBHelper.getInstance(context);
//		return instance;
//	}
//
//	/** ������� */
//	public long insert(M m) {
//		// ����ͨ�����ȡ��ݣ�������ݷ���ע�⣬��ע�����ݼ�Ϊ����ݿ����Ԫ��
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		String table = "";
//		String nullColumnHack = "";
//		fill(m,values);
//		long id = db.insert(table, null, values);
//		db.close();
//		
//		return id;
//
//	}
//
//	/**
//	 * �����ģ�͵���ݻ�ȡ��ContentValues;
//	 * 
//	 * @param m
//	 * @param values
//	 */
//	private void fill(M m, ContentValues values) {
//		// ��ȡ�ֶ�
//		// ��ȡ�ֶε�ע��
//		// �ж�ע�⣬���ע�����ͣ����ദ���ֶ�
//		// ������ע����ֶ��򲻴��ڴ���
//		Field[] fields = m.getClass().getDeclaredFields();
//		try {
//			for (Field field : fields) {
//				Annotation[] annotations = field.getAnnotations();
//				if (!field.isAnnotationPresent(Column.class)) {
//					continue;
//				}
//				Column column = (Column) field.getAnnotation(Column.class);
//				Object fieldValue;
//				field.setAccessible(true);
//				fieldValue = field.get(m);
//				if (fieldValue == null){
//                    continue;
//                }
//				values.put(column.value(), fieldValue.toString());
//			}
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	
//	
//	/** ��ݲ��������� */
//	public void setListener(onResultListener listener) {
//		this.lResultListener = listener;
//	}
//
//	/** �������ص� */
//	public interface onResultListener {
//
//	}
//}
