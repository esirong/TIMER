package com.esirong.timer.db.metadata;


import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author esirong@qq.com
 *
 */
public class TaskTable {

	public static final String TABLE_NAME = "tasks";
	public static final String ID = "_id";
	public static final String UUID = "uuid";
	public static final String TITLE = "title";
	public static final String NOTE = "note";
	public static final String CONTEXT_ID = "context_id";
	public static final String PRIORITY = "priority";
	public static final String T_LABELS_ID = "t_labels_id";
	public static final String START_AT = "start_at";
	public static final String END_AT = "end_at";
	public static final String STATUS = "status";
	public static final String T_GOAL_ID = "t_goal_id";
	public static final String FINISHED = "finished";
	public static final String SCORE = "score";

	/**
	 * ������
	 * @param paramSQLiteDatabase
	 */
	public void create(SQLiteDatabase paramSQLiteDatabase) {
		StringBuilder localStringBuilder = new StringBuilder();
		//װ��sql���
		localStringBuilder
				.append("CREATE TABLE ")
				.append(TABLE_NAME)
				.append("(")
				.append(ID+" integer primary key autoincrement,")
				.append(UUID+" varchar(32),")
				.append(TITLE+" varchar(20),")
				.append(NOTE+" varchar(200),")
				.append(CONTEXT_ID+" varchar(32),")
				.append(PRIORITY+" int(4),")
				.append(T_LABELS_ID+" varchar(32),")
				.append(START_AT+" BIGINT,")
				.append(END_AT+" BIGINT,")
				.append(STATUS+" varchar(32),")
				.append(T_GOAL_ID+" varchar(32),")
				.append(FINISHED+" boolean,")
				.append(SCORE+" int(3),")
				.append("CONSTRAINT context_id_fk FOREIGN KEY(context_id) REFERENCES context(uuid)")
				.append(");");
		paramSQLiteDatabase.execSQL(localStringBuilder.toString());
	}
	
	/**
	 * ɾ���
	 * @param paramSQLiteDatabase
	 */
	public void delete(SQLiteDatabase paramSQLiteDatabase) {
		
	}

}
