/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.esirong.timer.receiver;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.esirong.timer.Task;
import com.esirong.timer.db.AlertDao;
import com.esirong.timer.db.AlertImpl;
import com.esirong.timer.util.Toasts;

/**
 * 接受初始化的广播。包括开机关机，和晚上0点转换，时区的变化。都将触发重新设定时间
 * @author esirong
 *
 */
public class AlarmInitReceiver extends BroadcastReceiver {

	private AlertDao taskDao;
	@Override
	public void onReceive(Context context, Intent intent) {
		Toasts.showToastLong(context, "接受广播");

		// 获取需要提醒的事务
		List<Task> list = getAlertTasks(context);
		// 设置提醒
		initAlertTasks(context, list);
	}

	private void initAlertTasks(Context context, List<Task> list) {
		for (Task task : list) {
			long alertDate = task.getAlert_at();
			Intent sender = new Intent(context, AlarmReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, sender, 0);
			AlarmManager alermManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			alermManager.set(AlarmManager.RTC_WAKEUP, alertDate, pendingIntent);
		}

	}

	//获取需要提醒的事务
	private List<Task> getAlertTasks(Context context) {
		// TODO 获取提醒的事务
		taskDao = new AlertImpl(context);
		long currentDate = System.currentTimeMillis();
		List<Task> list = taskDao.findTask(currentDate);
		Toasts.showToastLong(context, "AlarmInitReceiver" + list);
		return list;

	}
}
