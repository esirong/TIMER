package com.esirong.timer.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.esirong.timer.R;
import com.esirong.timer.Address;
import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.Goal;
import com.esirong.timer.Label;
import com.esirong.timer.TaskDao;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.Task_Label;
import com.esirong.timer.adapter.ContextAdapter;
import com.esirong.timer.adapter.GoalAdapter;
import com.esirong.timer.adapter.GoalAdapter.ViewHolder;
import com.esirong.timer.adapter.LabelAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.DialogUtil;
import com.esirong.timer.util.L;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;
import com.esirong.timer.view.DateTimePickerDialog;
import com.esirong.timer.view.DateTimePickerDialog.OnDateTimeSetListener;
import com.esirong.timer.view.MenuItem;
import com.esirong.timer.view.PopupMenu.OnItemSelectedListener;

/**
 * 编辑页,可增加，可修改 展示事务的信息
 * 
 * @author esirong
 * 
 */
public class TaskEditActivity extends FragmentActivity implements
		OnClickListener, OnTouchListener, OnItemSelectedListener {

	private String taskTitle;
	private String taskType;
	private long startTime;
	private long endTime;
	private String taskContext;
	private String taskLabels;
	private String taskGoals;
	private String memo;
	private TextView mTtitle;
	private Button mType;
	private Button mTimes;
	private Button mLabels;
	private Button mGoals;
	private Button mAddress;
	private Button bt_cancel;
	private Button bt_Ok;

	// 开如时间
	private TextView mStartAt;
	// 结束时间
	private TextView mEndAt;
	private TextView mContext;
	private TextView mLabel;
	private TextView mGoal;
	// 备注
	private TextView memo_tv;
	// 弹出窗
	private PopupWindow popupWindow;
	PopupWindow labelPopup = null;
	private TaskDao2 dao;

	private final static int PLAY_SELECTION = 0;
	private final static int ADD_TO_PLAYLIST = 1;
	private final static int SEARCH = 2;

	// 任务
	com.esirong.timer.Task mTask = new com.esirong.timer.Task();
	private ArrayList<Label> labels = new ArrayList<Label>();
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private TaskDao taskDao;
	private SQLiteDatabase db;
	Calendar time = Calendar.getInstance(Locale.CHINESE);
	protected int checkLabelNum;
	protected int checkGoalNum;
	protected int checkContextNum;

	public static final String KEY_TASK = "task_key";
	private static final String TAG = "EditActivity";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		dao = new TaskDao2(getApplicationContext());
		initView();
		initData();

	}

	/**
	 * 初始化
	 */
	private void initView() {

		bt_cancel = (Button) findViewById(R.id.cancel_btn);
		bt_Ok = (Button) findViewById(R.id.save_btn);
		//
		mType = (Button) findViewById(R.id.edit_type_btn);
		mTtitle = (EditText) findViewById(R.id.edit_title);
		mAddress = (Button) findViewById(R.id.edit_context_btn);
		mLabels = (Button) findViewById(R.id.edit_label_btn);
		mTimes = (Button) findViewById(R.id.edit_time_btn);
		mGoals = (Button) findViewById(R.id.edit_goal_btn);
		//
		mContext = (TextView) findViewById(R.id.edit_context_tv);
		mLabel = (TextView) findViewById(R.id.edit_labels_tv);
		mGoal = (TextView) findViewById(R.id.edit_goals_tv);

		mStartAt = (TextView) findViewById(R.id.edit_time_start_tv);
		mEndAt = (TextView) findViewById(R.id.edit_time_end_tv);
		memo_tv = (TextView) findViewById(R.id.memo_tv);

		setOnListener();

	};

	private void setOnListener() {
		bt_cancel.setOnClickListener(this);
		bt_Ok.setOnClickListener(this);
		//
		mType.setOnClickListener(this);
		mTtitle.setOnClickListener(this);

		mAddress.setOnClickListener(this);
		mLabels.setOnClickListener(this);
		mTimes.setOnClickListener(this);
		mGoals.setOnClickListener(this);
		memo_tv.setOnClickListener(this);
		mStartAt.setOnClickListener(this);
		mEndAt.setOnClickListener(this);
		mContext.setOnClickListener(this);
		mLabel.setOnClickListener(this);
		mGoal.setOnClickListener(this);

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		Intent intent = getIntent();
		mTask = intent.getParcelableExtra(KEY_TASK);
		if (mTask == null) {
			//init one task;
			mTask = new com.esirong.timer.Task();
			mTask.setAlert_at(time.getTimeInMillis());
			mTask.setStart_at(time.getTimeInMillis());
			mTask.setEnd_at(time.getTimeInMillis()+1*60*60*1000);
			mTask.setDone(false);
			mTask.setFinished(false);
			mTask.setType("type1");
			mTask.setScore(0);
			mTask.setStatus(0);
			taskType = "type1";
			startTime = time.getTimeInMillis();
			endTime  =time.getTimeInMillis()+1*60*60*1000;
		}
	}

	/**
	 * 
	 * @param v
	 */
	public void showTimePickerDialog(View v) {
		L.i(TAG, "showTimePickerDialog");
		Calendar cal = Calendar.getInstance();
		DateTimePickerDialog timePicker = new DateTimePickerDialog(this,
				cal.getTimeInMillis());
		switch (v.getId()) {
		case R.id.edit_time_start_tv:
			timePicker.setTitle("开始时间");
			timePicker.setIcon(R.drawable.time_flag);
			timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

				@Override
				public void OnDateTimeSet(AlertDialog dialog, long date) {
					mTask.setStart_at(date);
					mStartAt.setText(updateTitle(date));
					startTime = date;
				}
			});

			break;
		case R.id.edit_time_end_tv:
			timePicker.setTitle("结束时间");
			timePicker.setOnDateTimeSetListener(new OnDateTimeSetListener() {

				@Override
				public void OnDateTimeSet(AlertDialog dialog, long date) {
					mTask.setEnd_at(date);
					mEndAt.setText(updateTitle(date));
					endTime = date;
				}
			});

			break;
		}

		timePicker.show();

	}

	@Override
	public void onClick(View v) {
		// TODO 处理所有点击事件
		if (v == mTtitle) {
		} else if (v == mAddress) {
			showAddressWindow(v);
		} else if (v == mTimes) {
			showTimePickerDialog(v);
		} else if (v == mStartAt) {
			showTimePickerDialog(v);
		} else if (v == mEndAt) {
			showTimePickerDialog(v);
		} else if (v == bt_cancel) {
			finish();
		} else if (v == bt_Ok) {
			save();
		} else if (v == mType) {
			showTypeWindow(v);
		} else if (v == mLabels) {
			showLabelWindow(v);
		} else if (v == mGoals) {
			showGoalWindow(v);
		} else if (v == memo_tv) {
		} else if (v == mContext) {
			showAddressWindow(v);
		} else if (v == mLabel) {
			showLabelWindow(v);
		} else if (v == mGoal) {
			showGoalWindow(v);
		}

	}

	private HashMap<String, Boolean> isGoalSelected = GoalAdapter
			.getIsSelected();

	private void showGoalWindow(View v) {
		final DialogUtil dialog = new DialogUtil(this);
		dialog.setContentView(R.layout.todo_location_dialog);
		dialog.setTitle("目标");
		ListView listview = (ListView) dialog.findViewById(R.id.list);
		final TextView textview = (TextView) dialog.findViewById(R.id.textview);
		final EditText add_new = (EditText) dialog.findViewById(R.id.add_new);

		ArrayList<Goal> list = dao.findGoalAll();
		final GoalAdapter adapter = new GoalAdapter(list, this);

		if (isGoalSelected != null) {
			GoalAdapter.setIsSelected(isGoalSelected);
		}
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GoalAdapter.ViewHolder holder = (GoalAdapter.ViewHolder) view
						.getTag();
				holder.cb.toggle();

				String name = holder.tv.getText().toString();
				GoalAdapter.getIsSelected().put(name, holder.cb.isChecked());

			}
		});

		dialog.show();
		final ArrayList selectedList = new ArrayList();

		dialog.findViewById(R.id.ok_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.cancel();

						taskGoals = textview.getText().toString();
						HashMap<String, Boolean> map = GoalAdapter
								.getIsSelected();
						isGoalSelected = (HashMap<String, Boolean>) map.clone();
						for (Entry<String, Boolean> set : map.entrySet()) {
							if (set.getValue()) {
								String index = set.getKey();
								selectedList.add(index);
							}
						}
						mGoal.setText(Strings.join(selectedList, ","));
					}
				});

		dialog.findViewById(R.id.cancel_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.edit_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 保存
						String str = add_new.getText().toString();
						if (Strings.isEmptyOrNull(str)) {
							Toasts.showToastShort(getApplicationContext(),
									"请输入有效字符");
							return;
						}
						Goal goal = new Goal();
						goal.setName(str);
						// 保存到数据库
						// 是不是已有此字段在数据库
						if (dao.isGoalSaved(goal)) {
							// 获取
							goal = dao.findGoal(goal.getName());
							Toasts.showToastShort(getApplicationContext(),
									"已添加");
							// adapter.updateItem();
						} else {
							// 新增
							long id = dao.insertGoal(goal);
							goal = dao.findGoal(id);
							adapter.addList(goal);
						}
						adapter.getIsSelected().put(goal.getName(), true);
						// 保存它是被选中
						// 保存到适配器里去
						// 更新
						adapter.notifyDataSetChanged();

					}
				});
	}

	private void showAddressWindow(View view) {

		final DialogUtil dialog = new DialogUtil(this);

		dialog.setContentView(R.layout.todo_location_dialog2);
		dialog.setTitle("情境");
		final ListView listview = (ListView) dialog.findViewById(R.id.list2);
		final TextView textview = (TextView) dialog.findViewById(R.id.textview);
		final EditText add_new = (EditText) dialog.findViewById(R.id.add_new);
		// 数据源

		final ArrayList<Address> list = dao.findAddressAll();
		final ContextAdapter adapter = new ContextAdapter(this, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		dialog.show();
		dialog.findViewById(R.id.ok_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						// 根据RadioButton的选择情况确定用户名
						HashMap<String, Boolean> selected = adapter.getStatus();
						for (Entry<String, Boolean> entity : selected
								.entrySet()) {
							if (entity.getValue()) {
								taskContext = entity.getKey();
								mContext.setText(taskContext);
							}

						}
						dialog.cancel();
					}
				});
		dialog.findViewById(R.id.cancel_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
		// 增加
		dialog.findViewById(R.id.edit_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 保存
						String str = add_new.getText().toString();
						if (Strings.isEmptyOrNull(str)) {
							return;
						}
						Address address = new Address();
						address.setName(str);
						// 保存到数据库
						// 是不是已有此字段在数据库
						if (dao.isAddressSaved(address)) {
							// 获取
							address = dao.findAddress(address.getName());
							Toasts.showToastShort(getApplicationContext(),
									"已添加");
							// adapter.updateItem();
						} else {
							// 新增
							long id = dao.insertAddress(address);
							address = dao.findAddress(address.getName());
							adapter.addList(address);
							// 用TextView显示

						}

						HashMap<String, Boolean> selectedMap = adapter
								.getStatus();
						if (selectedMap.containsKey(address.getName())) {
						} else {
							checkLabelNum++;

						}
						for (String key : selectedMap.keySet()) {
							selectedMap.put(key, false);

						}
						selectedMap.put(address.getName(), true);
						adapter.notifyDataSetChanged();

					}
				});
	}

	private HashMap<String, Boolean> islabelSelected = LabelAdapter
			.getIsSelected();

	private void showLabelWindow(View parent) {
		final DialogUtil dialog = new DialogUtil(this);

		dialog.setContentView(R.layout.todo_location_dialog);
		dialog.setTitle("标签");
		ListView listview = (ListView) dialog.findViewById(R.id.list);
		final TextView textview = (TextView) dialog.findViewById(R.id.textview);
		final EditText add_new = (EditText) dialog.findViewById(R.id.add_new);
		// 数据源
		ArrayList<Label> list = dao.findLabelAll();
		final LabelAdapter adapter = new LabelAdapter(list, this);

		if (islabelSelected != null) {
			LabelAdapter.setIsSelected(islabelSelected);
		}

		listview.setAdapter(adapter);
		textview.setText("已选择" + checkLabelNum + "项");

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LabelAdapter.ViewHolder holder = (LabelAdapter.ViewHolder) view.getTag();
				holder.cb.toggle();

				// 将CheckBox的选中状况记录下来
				LabelAdapter.getIsSelected().put(
						holder.tv.getText().toString(), holder.cb.isChecked());
			}
		});

		dialog.show();
		final ArrayList selectedList = new ArrayList();
		dialog.findViewById(R.id.ok_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.cancel();
						taskLabels = textview.getText().toString();
						HashMap<String, Boolean> map = LabelAdapter
								.getIsSelected();
						islabelSelected = (HashMap<String, Boolean>) map
								.clone();
						for (Entry<String, Boolean> set : map.entrySet()) {
							if (set.getValue()) {
								String index = set.getKey();
								selectedList.add(index);
							}
						}
						mLabel.setText(Strings.join(selectedList, ","));
					}
				});
		dialog.findViewById(R.id.cancel_bt).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.edit_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 保存
						String str = add_new.getText().toString();
						if (Strings.isEmptyOrNull(str)) {
							return;
						}
						Label label = new Label();
						label.setName(str);
						// 保存到数据库
						// 是不是已有此字段在数据库
						if (dao.isSaved(label)) {
							// 获取
							label = dao.findLabel(label.getName());
							Toasts.showToastShort(getApplicationContext(),
									"已添加");
							// adapter.updateItem();
						} else {
							// 新增
							long id = dao.insertLabel(label);
							label = dao.findLabel(id);
							adapter.addList(label);
							// 用TextView显示

						}

						HashMap<String, Boolean> selectedMap = adapter
								.getIsSelected();
						if (selectedMap.containsKey(label.getName())) {
						} else {
							checkLabelNum++;

						}
						selectedMap.put(label.getName(), true);
						adapter.notifyDataSetChanged();

					}
				});
		;
	}

	// 展示弹出窗
	private void showTypeWindow(View parent) {
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
					mType.setBackgroundResource(R.drawable.type1);
					mTask.setType("type1");
					popupWindow.dismiss();
				}
			});
			view2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					mType.setBackgroundResource(R.drawable.type2);
					mTask.setType("type2");
					popupWindow.dismiss();
				}
			});
			view3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					mType.setBackgroundResource(R.drawable.type3);
					mTask.setType("type3");
					popupWindow.dismiss();
				}
			});
			view4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					mType.setBackgroundResource(R.drawable.type4);
					mTask.setType("type4");
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
		popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	private void save() {
		L.i(TAG, "保存");
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "AWEEK.db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		taskDao = daoSession.getTaskDao();
		// 标题，类型，时间，情境，标签，目标，备注，其他默认

		taskTitle = mTtitle.getText().toString();
		taskType = mType.getText().toString();
		taskContext = mContext.getText().toString();
		taskLabels = mLabel.getText().toString();
		taskGoals = mGoal.getText().toString();
		memo = memo_tv.getText().toString();

		if (Strings.isBlank(taskTitle)) {
			Toasts.showToastShort(getApplicationContext(), "请输入标题");
			return;
		}

		//
		mTask.setTitle(taskTitle);
//		 mTask.setType(taskType);
		mTask.setStart_at(startTime);
		mTask.setEnd_at(endTime);
		mTask.setNote(memo);
		mTask.setAlert_at(startTime);
		long taskId = dao.insertTask(mTask);

		// s标签_事务
		if (null != islabelSelected && !(islabelSelected.isEmpty())) {
			for (Entry<String, Boolean> entity : islabelSelected.entrySet()) {
				Label label = dao.findLabel(entity.getKey());
				Task_Label task_Label = new Task_Label();

				task_Label.setTaskId(taskId);
				task_Label.setLabelId(label.getId());
				if (dao.isTaskLabelSaved(task_Label)) {
					continue;
				}

				long TLId = dao.insertTaskLabel(task_Label);
			}
		}
		if (null != isGoalSelected && !isGoalSelected.isEmpty()) {
			for (Entry<String, Boolean> entity : isGoalSelected.entrySet()) {
				Goal goal = dao.findGoal(entity.getKey());
				Task_Goal task_goal = new Task_Goal();

				task_goal.setTaskId(taskId);
				task_goal.setGoalId(goal.getId());
				if (dao.isTaskGoalSaved(task_goal)) {
					continue;
				}

				long TLId = dao.insertTaskGoal(task_goal);
			}
		}
		// 调用数据库工具，写入保存

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			default:
				break;
			}
		}
		return false;
	}

	private String updateTitle(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH：mm");
		return format.format(date);
	}

	@Override
	public void onItemSelected(MenuItem item) {

	}
}
