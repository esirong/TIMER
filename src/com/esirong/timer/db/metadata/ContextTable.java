package com.esirong.timer.db.metadata;

import android.database.sqlite.SQLiteDatabase;
/**
 * contexts��<br>
 * �龳��,��¼��������龳
 * @author Administrator
 * 
 */
public class ContextTable {
    public static final String TABLE_NAME = "contexts";
    public static final String ID = "_id";
    public static final String UUID = "uuid";
    public static final String NAME = "name";
    public static final String POS = "pos";
    public static final String table_name = "";

    /**
     * �����龳��
     * 
     * @param paramSQLiteDatabase
     */
    public static void create(SQLiteDatabase paramSQLiteDatabase) {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder
        .append("CREATE TABLE ")
        .append(TABLE_NAME)
        .append("(")
        .append(ID + " integer primary key autoincrement,")
        .append(UUID + " varchar(32),")
        .append(NAME + " varchar(20),")
        .append(POS + " NUMERIC")
        .append(");");
        
        paramSQLiteDatabase.execSQL(localStringBuilder.toString());
    }
}
