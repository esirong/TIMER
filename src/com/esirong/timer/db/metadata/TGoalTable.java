package com.esirong.timer.db.metadata;


import android.database.sqlite.SQLiteDatabase;
/**
 * ���ϱ�
 * ����:task_uuid
 * Ŀ��:goal_uuid
 * @author Administrator
 *
 */
public class TGoalTable {
	public static final String TABLE_NAME = "t_goals";
	public static final String ID = "_id";
	public static final String TASK_UUID = "task_uuid";
	public static final String GOAL_UUID = "goal_uuid";
	public static final String NAME = "name";
	

	public static void create(SQLiteDatabase paramSQLiteDatabase) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder
		.append("CREATE TABLE ")
		.append(TABLE_NAME)
		.append("(")
		.append(ID+" integer primary key autoincrement,")
		.append(TASK_UUID+" varchar(32),")
		.append(GOAL_UUID+" varchar(32),")
		.append(NAME+" varchar(32),")
		.append("CONSTRAINT task_uuid_fk FOREIGN KEY(task_uuid) REFERENCES tasks(uuid),")
		.append("CONSTRAINT goal_uuid_fk FOREIGN KEY(goal_uuid) REFERENCES goals(uuid)")
		.append(");");

		paramSQLiteDatabase.execSQL(localStringBuilder.toString());
	}
	 
	
}
