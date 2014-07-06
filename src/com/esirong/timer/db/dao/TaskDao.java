package com.esirong.timer.db.dao;

import java.sql.Date;
import java.util.ArrayList;

import com.esirong.timer.Task;

public interface TaskDao {

	public abstract Task findbyId(String uuid);

	public abstract ArrayList<Task> findTasks();

	public abstract ArrayList<Task> findTasksByDate(Date date);

	public abstract ArrayList<Task> findTasksByDate();

	public abstract ArrayList<Task> findTasksByNow();

	public abstract int getTotalTaskCount();

	public abstract int getCountForDone();

	public abstract int getCountForUndone();

	public abstract int getEfficientPercent();

	public abstract int getPercentType(int taskType);

}