package com.esirong.timer.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * 事务后台服务
 * <p>计算时间的流动。事务的开始和停止的动态广播发出。
 * @author esirong
 *
 */
public class TaskService extends IntentService {

	public TaskService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		
	}

}
