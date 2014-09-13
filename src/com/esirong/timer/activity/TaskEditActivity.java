package com.esirong.timer.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.esirong.timer.Goal;
import com.esirong.timer.Label;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.Task_Label;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;
import com.esirong.timer.view.CalculateFragment;
import com.esirong.timer.view.DateTimePickerDialog;
import com.esirong.timer.view.DateTimePickerDialog.OnDateTimeSetListener;
import com.esirong.timer.view.picker.OnDateSelectedListener;
import com.esirong.timer.view.picker.ReminderDatePicker;

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
	/** 事务类型图标 */
	private ImageView AvatarIcon;
	private View startAt;
	private View endAt;
	/** 开始时间 */
	private TextView start_time_at_tv;
	private TextView start_date_at_tv;
	/** 结束时间 */
	private TextView end_time_at_tv;
	private TextView end_date_at_tv;
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
	 private ReminderDatePicker datePicker;
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
		AvatarIcon = (ImageView) findViewById(R.id.AvatarIcon);
		startAt = findViewById(R.id.start_at);
		endAt = findViewById(R.id.end_at);
		 datePicker = (ReminderDatePicker) findViewById(R.id.date_picker);
		// setup listener for a date change:
	        datePicker.setOnDateSelectedListener(new OnDateSelectedListener() {
	            @Override
	            public void onDateSelected(Calendar date) {
	                mTask.setAlert_at(date.getTimeInMillis());
	                dao.insertTask(mTask);
	                Intent sender = new Intent(instance, com.esirong.timer.receiver.AlarmReceiver.class);
//	                sender.putExtra(AlarmAlertActivity.TASK_KEY, mTask.getId());
	                PendingIntent pendingIntent = PendingIntent.getBroadcast(instance, 0, sender, PendingIntent.FLAG_UPDATE_CURRENT);
	                AlarmManager alermManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	                alermManager.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
	                Toast.makeText(TaskEditActivity.this, "Selected date: "+ getDateFormat().format(date.getTime()), Toast.LENGTH_SHORT).show();
	            }
	        });
		 start_date_at_tv = (TextView)findViewById(R.id.start_date_at_tv);
		start_time_at_tv = (TextView) findViewById(R.id.start_time_at_tv);
		end_date_at_tv = (TextView) findViewById(R.id.end_date_at_tv);
		end_time_at_tv = (TextView) findViewById(R.id.end_time_at_tv);
		allDay = (Button) findViewById(R.id.allday_btn);
		//初设默认时间
		Calendar cal = Calendar.getInstance();
		start_date_at_tv.setText(formateDate(cal.getTimeInMillis()));
		start_time_at_tv.setText(formateDate(cal.getTimeInMillis()));
		end_date_at_tv.setText(formateDate(cal.getTimeInMillis()+3600));
		end_time_at_tv.setText(formateDate(cal.getTimeInMillis()+3600));
		
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
		//
		goal_tv = (TextView) findViewById(R.id.goal_tv);
		label_tv = (TextView) findViewById(R.id.label_tv);
		location_tv = (TextView) findViewById(R.id.location_tv);
		reminder_tv = (TextView) findViewById(R.id.reminder_tv);
		// ==============
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
		AvatarIcon.setOnClickListener(this);
		title.addTextChangedListener(textWatcher);

	}

	
	    private java.text.DateFormat savedFormat;
	    public java.text.DateFormat getDateFormat() {
	        if(savedFormat == null)
	            savedFormat = DateFormat.getDateTimeInstance();
	        return savedFormat;
    }

    TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {

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
				add_btn.setClickable(false);
				panel.setVisibility(View.GONE);
			} else {
				operation_panel.setVisibility(View.VISIBLE);
				add_btn.setVisibility(View.VISIBLE);
				add_btn.setClickable(true);
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
			startAt.requestFocus();
		} else if (v == delete_btn) {
			deleteTask();
			finish();
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
		} else if (v == AvatarIcon) {
			showTypePooupWindow(v);
		} else if (v == reminder) {
		}

	}
	PopupWindow popupWindow = null;
	private void showTypePooupWindow(View v) {
			// 从xml制成一个view
			ImageView view1 = null;
			ImageView view2 = null;
			ImageView view3 = null;
			ImageView view4 = null;
			if (popupWindow == null) {
				
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View view = inflater.inflate(R.layout.type_select_layout,
						null);
				view1 = (ImageView) view.findViewById(R.id.task_type_1);
				view2 = (ImageView) view.findViewById(R.id.task_type_2);
				view3 = (ImageView) view.findViewById(R.id.task_type_3);
				view4 = (ImageView) view.findViewById(R.id.task_type_4);
				view.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						return false;
					}

				});
				view1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AvatarIcon.setBackgroundResource(R.drawable.type1);
						mTask.setType("type1");
						dao.insertTask(mTask);
						popupWindow.dismiss();
					}
				});
				view2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AvatarIcon.setBackgroundResource(R.drawable.type2);
						mTask.setType("type2");
						dao.insertTask(mTask);
						popupWindow.dismiss();
					}
				});
				view3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AvatarIcon.setBackgroundResource(R.drawable.type3);
						mTask.setType("type3");
						dao.insertTask(mTask);
						popupWindow.dismiss();
					}
				});
				view4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AvatarIcon.setBackgroundResource(R.drawable.type4);
						mTask.setType("type4");
						dao.insertTask(mTask);
						popupWindow.dismiss();
					}
				});

				popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
			}
			// 使其聚集
			popupWindow.setFocusable(true);
			// 设置允许在外点击消失
			popupWindow.setOutsideTouchable(true);

			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			// 设置SelectPicPopupWindow弹出窗体的背景
			popupWindow.update();
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.showAtLocation(v, Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_HORIZONTAL, 0, 0); 
	}

		

	private void showGoalDialog() {
		GoalDialog dialog = new GoalDialog(instance);
		dialog.initDialog(dao.findGoalAll(), mTask);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				List<Task_Goal> list = dao.findGoalByTaskId(mTask.getId());
				if (list == null || list.size() <= 0) {
					goal.setVisibility(View.GONE);
				} else {
					goal.setVisibility(View.VISIBLE);
					String str = "";
					for (Task_Goal l : list) {
						Goal goal = dao.findGoal(l.getGoalId());
						str += goal.getName();
						str += ",";
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
				List<Task_Label> list = dao.findLabelByTaskId(mTask.getId());
				if (list == null || list.size() <= 0) {
					label.setVisibility(View.GONE);
				} else {
					label.setVisibility(View.VISIBLE);
					String str = "";
					for (Task_Label l : list) {
						Label label = dao.findLabel(l.getLabelId());
						str += label.getName();
						str += ",";
					}
					label_tv.setText(str);
				}

			}
		});
		dialog.show();

	}

	private void showLocationDialog() {
		LocationDialog dialog = new LocationDialog(instance);
		dialog.initDialog(dao.findAddressAll(), mTask);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				String str = mTask.getAddress();
				if (Strings.isBlank(str)) {
					location.setVisibility(View.GONE);

				} else {
					location.setVisibility(View.VISIBLE);
					location_tv.setText(str);
				}

			}
		});
		dialog.show();
	}

	// 提醒
	private void showReminderDialog() {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(instance,
				cal.getTimeInMillis());
		timePicker.setTitle("提醒时间");
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.set24HourView(true);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				
				mTask.setAlert_at(date);
				dao.insertTask(mTask);
				if (date <= 0) {
					reminder.setVisibility(View.GONE);
				} else {
					reminder.setVisibility(View.VISIBLE);
					reminder_tv.setText(formateDate(date)+formateTime(date));
					//设定提醒
				}
                Intent sender = new Intent(instance, com.esirong.timer.receiver.AlarmReceiver.class);
//                sender.putExtra(AlarmAlertActivity.TASK_KEY, mTask.getId());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(instance, 0, sender, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alermManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alermManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
			}
		});
		timePicker.show();

	}

	private void switchALLDay() {

	}

	private void showEndTimeDialog() {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(instance,
				cal.getTimeInMillis());
		timePicker.setTitle("结束时间");
		timePicker.set24HourView(true);
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				mTask.setEnd_at(date);
				end_date_at_tv.setText(formateDate(date));
				end_time_at_tv.setText(formateTime(date));
			}
		});
		timePicker.show();
	}

	private void showStartTimeDialog() {
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(instance,
				cal.getTimeInMillis());
		timePicker.setTitle("开始时间");
		timePicker.set24HourView(true);
		timePicker.setIcon(R.drawable.time_flag);
		timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

			@Override
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				mTask.setStart_at(date);
				start_date_at_tv.setText(formateDate(date));
				start_time_at_tv.setText(formateTime(date));
			}
		});
		timePicker.show();

	}

	private void deleteTask() {
		Long id = mTask.getId();
		if (id != null) {
			dao.deleteTask(id);
		}

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

	private String formateDate(long date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 ");

		return sdf.format(c.getTime());

	}
	private String formateTime(long date) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒 E ");

		return sdf.format(c.getTime());

	}
}
