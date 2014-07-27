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
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.Task;
import com.esirong.timer.db.AlertDao;
import com.esirong.timer.db.AlertImpl;


public class AlarmInitReceiver extends BroadcastReceiver {

//    private static final String [] PROJECTION = new String [] {
//        NoteColumns.ID,
//        NoteColumns.ALERTED_DATE
//    };

    private static final int COLUMN_ID                = 0;
    private static final int COLUMN_ALERTED_DATE      = 1;
    private DaoMaster daoMaster;
	private DaoSession daoSession;
	private AlertDao taskDao;
	private SQLiteDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {
        long currentDate = System.currentTimeMillis();
        taskDao = new AlertImpl(context) ;
        List<Task> list = taskDao.findTask(currentDate);
        Toast.makeText(context, "AlarmInitReceiver"+list, 0).show();
		for(Task task :list){
			//TODO 一个任务可能有多次提醒
			
			 long alertDate = task.getAlert_at();
             Intent sender = new Intent(context, AlarmReceiver.class);
             PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, sender, 0);
             AlarmManager alermManager = (AlarmManager) context
                     .getSystemService(Context.ALARM_SERVICE);
            
             alermManager.set(AlarmManager.RTC_WAKEUP, alertDate, pendingIntent);
		}
		
		
//        Cursor c = context.getContentResolver().query(Notes.CONTENT_NOTE_URI,
//                PROJECTION,
//                NoteColumns.ALERTED_DATE + ">? AND " + NoteColumns.TYPE + "=" + Notes.TYPE_NOTE,
//                new String[] { String.valueOf(currentDate) },
//                null);
//
//        if (c != null) {
//            if (c.moveToFirst()) {
//                do {
//                    long alertDate = c.getLong(COLUMN_ALERTED_DATE);
//                    Intent sender = new Intent(context, AlarmReceiver.class);
//                    sender.setData(ContentUris.withAppendedId(Notes.CONTENT_NOTE_URI, c.getLong(COLUMN_ID)));
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, sender, 0);
//                    AlarmManager alermManager = (AlarmManager) context
//                            .getSystemService(Context.ALARM_SERVICE);
//                    alermManager.set(AlarmManager.RTC_WAKEUP, alertDate, pendingIntent);
//                } while (c.moveToNext());
//            }
//            c.close();
//        }
    }
}
