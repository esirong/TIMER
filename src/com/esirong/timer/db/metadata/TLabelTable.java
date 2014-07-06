package com.esirong.timer.db.metadata;


import android.database.sqlite.SQLiteDatabase;

public class TLabelTable {
	public static final String TABLE_NAME = "t_labels";
	public static final String ID = "_id";
	public static final String TASK_UUID = "task_uuid";
	public static final String LABEL_UUID = "label_uuid";
	public static final String NAME = "name";
	
	public static void create(SQLiteDatabase paramSQLiteDatabase){
		StringBuilder LocalStringBuilder = new StringBuilder();
		
		LocalStringBuilder
		.append("create table ")
		.append(TABLE_NAME)
		.append("(")
		.append(ID+" integer primary key autoincrement,")
		.append(TASK_UUID+" varchar(32),")
		.append(LABEL_UUID+" varchar(32),")
		.append(NAME+" varchar(32),")
		.append("CONSTRAINT task_uuid_fk FOREIGN KEY(task_uuid) REFERENCES tasks(uuid),")
		.append("CONSTRAINT task_uuid_fk FOREIGN KEY(task_uuid) REFERENCES tasks(uuid)")
		.append(");");
		
		paramSQLiteDatabase.execSQL(LocalStringBuilder.toString());
	}

}
