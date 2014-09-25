package com.esirong.timer.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoSession;
import com.esirong.timer.Task;
import com.esirong.timer.TaskDao;
import com.esirong.timer.DaoMaster.DevOpenHelper;

import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * 提醒数据管理帮助类
 * @author esirong
 *
 */
public class AlertDaoImpl implements AlertDao {
	private DaoSession daoSession;
	public AlertDaoImpl(Context cxt){
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(cxt, "AWEEK.db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}
	@Override
    public List<Task> findTask(long time) {
        TaskDao dao = daoSession.getTaskDao();
        QueryBuilder<Task> qb = dao.queryBuilder();
        qb.where( com.esirong.timer.TaskDao
                .Properties.Alert_at.ge(time));
        LazyList<Task> list = qb.listLazy();
        ArrayList<Task> tasks = new ArrayList<Task>(list);
        return tasks;
        
        
    }
    
    public List<Task> findTaskInAfter(long time) {
        TaskDao dao = daoSession.getTaskDao();
        QueryBuilder<Task> qb = dao.queryBuilder();
        qb.where( com.esirong.timer.TaskDao
                .Properties.Alert_at.ge(time));
        LazyList<Task> list = qb.listLazy();
        ArrayList<Task> tasks = new ArrayList<Task>(list);
        return tasks;
        
        
    }
    

}