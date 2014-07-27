package com.esirong.timer.db;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;

/**
 * 提醒
 * @author esirong
 *
 */
public interface AlertDao {
	
	public List findTask(long time);
}
