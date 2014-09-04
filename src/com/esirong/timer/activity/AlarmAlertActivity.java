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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.L;
import com.tandong.bottomview.view.BottomView;

/**
 * 提醒的界面.
 * 
 * @author esirong
 */
public class AlarmAlertActivity extends Activity implements OnClickListener,
        OnDismissListener, android.view.View.OnClickListener {
    /**
     * 事务Id.
     */
    public static final String TASK_KEY = "task_id";
    private static final String TAG = "AlarmAlertActivity";
    /**
     * 数据库访问操作类.
     */
    private TaskDao2 dao;
    /**
     * .
     */
    private long mNoteId;
    private String mSnippet;
    /**
     * 媒体播放器，用来播放铃声.
     */
    MediaPlayer mPlayer;
    WakeLock mWakelock;
    /**
     * 线性布局.
     */
    private LinearLayout layout;

    /**
     * 按键：关闭.
     */
    private Button close;
    /**
     * 按键：延时.
     */
    private Button delay;
    /**
     * 按键：开始.
     */
    private Button start;
    /**
     * 按键：完成.
     */
    private Button done;
    private BottomView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(TAG, "onCreate");
        dao = new TaskDao2(this);
        setContentView(R.layout.activity_reminder);
        initViews();
        bottomView.showBottomView(false); 
        layout = (LinearLayout) findViewById(R.id.task_bar);
        layout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO 不做处理
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
            Bundle bundle = intent.getExtras();
            Long taskid = bundle.getLong(TASK_KEY, -1);
            Task task = dao.findTask(taskid);
            if (task != null) {
                TextView text = (TextView) findViewById(R.id.task_title);
                text.setText(task.getTitle());
                ImageView type = (ImageView) findViewById(R.id.task_type);
                type.setImageResource(R.drawable.type1);

                if ("type1".equals(task.getType())) {
                    type.setBackgroundResource(R.drawable.type1);

                }
                if ("type2".equals(task.getType())) {
                    type.setBackgroundResource(R.drawable.type2);
                }
                if ("type3".equals(task.getType())) {
                    type.setBackgroundResource(R.drawable.type3);
                }
                if ("type4".equals(task.getType())) {
                    type.setBackgroundResource(R.drawable.type4);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        mPlayer = new MediaPlayer();
        if (true) {
//             showActionDialog();
            // playAlarmSound();
        } else {
            finish();
        }
    }

    private void initViews() {
        close = (Button) findViewById(R.id.operation_close);
        delay = (Button) findViewById(R.id.operation_delay);
        start = (Button) findViewById(R.id.operation_start);
        done = (Button) findViewById(R.id.operation_done);
        bottomView = new BottomView(this, R.style.BottomViewTheme_Defalut, R.layout.activity_reminder);
        close.setOnClickListener(this);
        delay.setOnClickListener(this);
        start.setOnClickListener(this);
        done.setOnClickListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    protected final void onResume() {
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

    @Override
    public void onClick(View v) {
        if (v == close) {
            Toast.makeText(getApplicationContext(), "close", 0).show();
        } else if (v == delay) {
            Toast.makeText(getApplicationContext(), "delay", 0).show();
        } else if (v == start) {
            Toast.makeText(getApplicationContext(), "start", 0).show();
        } else if (v == done) {
            Toast.makeText(getApplicationContext(), "done", 0).show();
        }

    }
}
