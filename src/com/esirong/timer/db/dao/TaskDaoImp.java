package com.esirong.timer.db.dao;

import java.sql.Date;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.Task;

public class TaskDaoImp implements TaskDao {
	private DaoSession daoSession;
	private static TaskDaoImp instance;

	private TaskDaoImp(Context cxt) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(cxt, "AWEEK.db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	public static TaskDao newInstace(Context cxt) {
		if (instance == null) {
			instance = new TaskDaoImp(cxt);
		}
		return instance;
	}

	@Override
	public Task findbyId(String uuid) {

		return null;
	}

	@Override
	public ArrayList<Task> findTasks() {
		
		return null;
	}

	@Override
	public ArrayList<Task> findTasksByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Task> findTasksByDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Task> findTasksByNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalTaskCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCountForDone() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCountForUndone() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEfficientPercent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPercentType(int taskType) {
		// TODO Auto-generated method stub
		return 0;
	}

}
