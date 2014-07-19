package com.esirong.timer.activity;

import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esirong.timer.Goal;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;
import com.esirong.timer.view.CalculateFragment;
import com.esirong.timer.view.DateTimePickerDialog;
import com.esirong.timer.view.DateTimePickerDialog.OnDateTimeSetListener;

/**
 * 编辑页,可增加，可修改 展示事务的信息
 * 
 * @author esirong
 * 
 */
public class TaskEditActivity extends FragmentActivity implements
		OnClickListener {

	private TaskEditActivity instance = this;
	/** 类型 */
	private TaskType type;
	/** 标题 */
	private EditText title;
	/** 开始时间 */
	private TextView startAt;
	/** 结束时间 */
	private TextView endAt;
	/** 全天选项按键 */
	private Button allDay;
	/** 可选项面板 */
	private ViewGroup panel;
	// 面板内容
	private Button reminder_btn;
	private Button location_btn;
	private Button label_btn;
	private Button goal_btn;
	// 任务描述
	private TextView reminder_tv;
	private TextView location_tv;
	private TextView label_tv;
	private TextView goal_tv;
	private View reminder;
	private View location;
	private View label;
	private View goal;
	// 操作按键
	private ViewGroup operation_panel;
	private Button add_btn;
	private Button delete_btn;
	private Button modify_btn;

	private View reminder_;
	private View flowview;
	private FragmentManager fm;
	// 数据
	private TaskDao2 dao;
	private Task mTask;
	private final static String TASK_ID_KEY = "task_id";

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

		};
	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edit);
		dao = new TaskDao2(instance);
		mTask = new Task();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		fm = getSupportFragmentManager();
		initTask();
		initViews();
	}

	private void initTask() {
		Intent intent = getIntent();
		Long taskid = intent.getLongExtra(TASK_ID_KEY, -1);
		if (taskid == -1) {
			mTask = new Task();
			mTask.setType("type1");
			mTask.setStart_at(System.currentTimeMillis());
			mTask.setEnd_at(System.currentTimeMillis() + 1 * 60 * 60);
			mTask.setDone(false);
			mTask.setFinished(false);
			mTask.setStatus(1);
		} else {
			mTask = dao.findTask(taskid);
		}

	}

	private void initViews() {
		operation_panel = (ViewGroup) findViewById(R.id.operation_panel);
		add_btn = (Button) findViewById(R.id.add_btn);
		delete_btn = (Button) findViewById(R.id.delete_btn);
		modify_btn = (Button) findViewById(R.id.add_btn);
		operation_panel.setVisibility(View.GONE);
		//
		title = (EditText) findViewById(R.id.task_title);
		startAt = (TextView) findViewById(R.id.start_time_at_tv);
		endAt = (TextView) findViewById(R.id.end_time_at_tv);
		allDay = (Button) findViewById(R.id.allday_btn);
		// 可选项
		panel = (ViewGroup) findViewById(R.id.options_panel);
		reminder_btn = (Button) findViewById(R.id.reminder);
		location_btn = (Button) findViewById(R.id.location);
		label_btn = (Button) findViewById(R.id.label);
		goal_btn = (Button) findViewById(R.id.goal);
		panel.setVisibility(View.GONE);
		// 任务描述
		reminder = findViewById(R.id.reminder_);
		location = findViewById(R.id.location_);
		label = findViewById(R.id.label_);
		goal = findViewById(R.id.goal_);
		reminder.setVisibility(View.GONE);
		location.setVisibility(View.GONE);
		label.setVisibility(View.GONE);
		goal.setVisibility(View.GONE);
		goal_tv = (TextView) findViewById(R.id.goal_tv);

		flowview = findViewById(R.id.flowview);
		// 选项按键
		reminder_btn.setOnClickListener(this);
		location_btn.setOnClickListener(this);
		label_btn.setOnClickListener(this);
		goal_btn.setOnClickListener(this);
		//
		reminder.setOnClickListener(this);
		startAt.setOnClickListener(this);
		endAt.setOnClickListener(this);
		add_btn.setOnClickListener(this);
		delete_btn.setOnClickListener(this);
		title.setOnClickListener(this);
		title.addTextChangedListener(textWatcher);

	}

	TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			dao.insertTask(mTask);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			mTask.setTitle(s.toString());
			if (Strings.isBlank(s.toString())) {
				Toasts.showToastShort(instance, "请输入");
				operation_panel.setVisibility(View.GONE);
				panel.setVisibility(View.GONE);
			} else {
				operation_panel.setVisibility(View.VISIBLE);
				add_btn.setVisibility(View.VISIBLE);
			}

		}
	};

	@Override
	public void onClick(View v) {
		if (v == title) {
		} else if (v == startAt) {
			showStartTimeDialog();
		} else if (v == endAt) {
			showEndTimeDialog();
		} else if (v == add_btn) {
			saveTask();
		} else if (v == delete_btn) {
			deleteTask();
		} else if (v == allDay) {
			switchALLDay();
		} else if (v == reminder_btn) {
			showReminderDialog();
		} else if (v == location_btn) {
			showLocationDialog();
		} else if (v == label_btn) {
			showLabelDialog();
		} else if (v == goal_btn) {
			showGoalDialog();
		} else if (v == reminder) {
			reminder_.setVisibility(View.VISIBLE);
		}

	}

	private void showGoalDialog() {
		GoalDialog dialog = new GoalDialog(instance);
		dialog.initDialog(dao.findGoalAll(), mTask);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				List<Task_Goal> list = dao.findGoalByTaskId(mTask.getId());
				if(list ==null ||list.size()<=0){
					goal.setVisibility(View.GONE);
				}else{
					goal.setVisibility(View.VISIBLE);
					String str = "";
					for(Task_Goal l:list){
						Goal goal = dao.findGoal(l.getGoalId());
						str+=goal.getName();
						str+=",";
					}
					goal_tv.setText(str);
				}
				

			}
		});
		dialog.show();

	}

	private void showLabelDialog() {
		LabelDialog dialog = new LabelDialog(instance);
		dialog.initDialog(dao.findLabelAll(), mTask);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				label.setVisibility(View.VISIBLE);

			}
		});
		dialog.show();
		dialog.show();

	}

	private void showLocationDialog() {
		LocationDialog dialog = new LocationDialog(instance);
		dialog.initDialog(dao.findAddressAll(), mTask);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				location.setVisibility(View.VISIBLE);

			}
		});
		dialog.show();
		dialog.show();
	}

	private void showReminderDialog() {
		// TODO Auto-generated method stub

	}

	private void switchALLDay() {

	}

	private void showEndTimeDialog() {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(instance,
				cal.getTimeInMillis());
		timePicker.setTitle("结束时间");
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				mTask.setEnd_at(date);
			}
		});
		timePicker.show();
	}

	private void showStartTimeDialog() {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(instance,
				cal.getTimeInMillis());
		timePicker.setTitle("开始时间");
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				mTask.setStart_at(date);
			}
		});
		timePicker.show();

	}

	private void deleteTask() {
		dao.deleteTask();

	}

	private void saveTask() {
		dao.insertTask(mTask);
		if (mTask.getTitle() != null) {
			panel.setVisibility(View.VISIBLE);
		}

	}

	private void showTitleDialog(View v) {
		Fragment basefragment = new CalculateFragment();
		// 保存fragment状态
		// ft.addtobackstack(null)不行
		fm.beginTransaction().replace(R.id.fragment_container, basefragment)
				.commit();
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
				Math.max(240, flowview.getHeight()), 0);
		translateAnimation.setDuration(500);
		flowview.setVisibility(View.VISIBLE);
		flowview.startAnimation(translateAnimation);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			// openSearch();
			Toasts.showToastShort(this, "action_search");
			return true;
		case R.id.action_settings:
			Toasts.showToastShort(this, "action_settings");
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			return true;
		case android.R.id.home:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
