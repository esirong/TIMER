package com.esirong.timer.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoSession;
import com.esirong.timer.DaoMaster.DevOpenHelper;

public class AlertImpl implements AlertDao {
	private DaoSession daoSession;
	public AlertImpl(Context cxt){
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(cxt, "AWEEK.db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}
	@Override
	public List findTask(long time) {
	return null;
		
		
	}

}
