package com.esirong.timer.test;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.test.AndroidTestCase;

import com.esirong.timer.Task;
import com.esirong.timer.db.AlertDao;
import com.esirong.timer.db.AlertImpl;
import com.esirong.timer.util.L;

/**
 * 测试数据库操作
 * 
 * @author esirong
 * 
 */
public class DAOTest extends AndroidTestCase {
	private static final String TAG = "DAOTest";
	private SQLiteDatabase db;

	public void testCreate() {

		AlertDao taskDao = new AlertImpl(getContext());
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		L.d(TAG, date.toLocaleString());
		List<Task> list = taskDao.findTask(time);
		L.d(TAG, list);
		for(Task task :list){
			L.d(TAG, new Date(task.getAlert_at()).toLocaleString());
			L.d(TAG, task.getTitle());
		}

	}
	
}
