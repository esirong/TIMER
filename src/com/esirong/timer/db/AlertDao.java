package com.esirong.timer.db;

import java.util.List;

/**
 * 提醒
 * @author esirong
 *
 */
public interface AlertDao {
	
	public List findTask(long time);
}
