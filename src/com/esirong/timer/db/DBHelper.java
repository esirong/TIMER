package com.esirong.timer.db;
//package com.esirong.aweek.db;
//
//import com.esirong.aweek.db.metadata.TaskTable;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * ��ݸ�����
// */
//public class DBHelper extends SQLiteOpenHelper {
//	private static final String DATABASE_NAME = "aweek.db";
//	private static final int DATABASE_VERSION = 1;
//	private static DBHelper instance;
//	
//	private  DBHelper(Context context) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//	}
//	
//	/**
//	 * ��ȡ����ʵ��
//	 * @param context
//	 * @return
//	 */
//	public static DBHelper getInstance(Context context){
//        if(instance == null){
//            instance = new DBHelper(context);
//        }
//        return instance;
//    }
//	
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		new TaskTable().create(db);
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		// TODO 
//
//	}
//
//}
