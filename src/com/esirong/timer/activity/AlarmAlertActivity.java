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

package com.esirong.timer.activity;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.esirong.timer.R;

//标题：操作（关闭，延时，完成，）
//延时：5，10，1h,明天
public class AlarmAlertActivity extends Activity implements OnClickListener,
		OnDismissListener {
	private long mNoteId;
	private String mSnippet;
	MediaPlayer mPlayer;
	WakeLock mWakelock;
	private LinearLayout layout; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_reminder);
		layout = (LinearLayout) findViewById(R.id.task_bar); 
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		if (!isScreenOn()) {
			win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
		}

		Intent intent = getIntent();

		try {
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return;
		}

		mPlayer = new MediaPlayer();
		if (true) {
			// showActionDialog();
			// playAlarmSound();
		} else {
			finish();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private boolean isScreenOn() {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		return pm.isScreenOn();
	}

	private void playAlarmSound() {
		Uri url = RingtoneManager.getActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_ALARM);

		int silentModeStreams = Settings.System.getInt(getContentResolver(),
				Settings.System.MODE_RINGER_STREAMS_AFFECTED, 0);

		if ((silentModeStreams & (1 << AudioManager.STREAM_ALARM)) != 0) {
			mPlayer.setAudioStreamType(silentModeStreams);
		} else {
			mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
		}
		try {
			mPlayer.setDataSource(this, url);
			mPlayer.prepare();
			mPlayer.setLooping(true);
			mPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showActionDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(mSnippet);
		dialog.setPositiveButton(R.string.ok, this);
		if (isScreenOn()) {
			dialog.setNegativeButton(R.string.cancel, this);
		}
		dialog.show().setOnDismissListener(this);
	}

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_NEGATIVE:
			Intent intent = new Intent(this, TaskEditActivity.class);
			intent.setAction(Intent.ACTION_VIEW);
			intent.putExtra(Intent.EXTRA_UID, mNoteId);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public void onDismiss(DialogInterface dialog) {
		stopAlarmSound();
		finish();
	}

	private void stopAlarmSound() {
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	protected void onPause() {

		super.onPause();
		if (mWakelock != null) {
			mWakelock.release();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		stopAlarmSound();
		finish();
	}
}
