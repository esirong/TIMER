package com.esirong.timer.db;

import java.util.List;

import com.esirong.timer.Task;

/**
 * 提醒
 * @author esirong
 *
 */
public interface AlertDao {
	
	public List<Task> findTask(long time);
}
