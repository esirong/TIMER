package com.esirong.timer.view;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.activity.AlarmAlertActivity;
import com.esirong.timer.activity.RemindActivity;
import com.esirong.timer.adapter.TaskListItemAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.L;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView.OnActionClickListener;

public class TaskView extends FrameLayout implements View.OnClickListener,
		AdapterView.OnItemClickListener {
	private static final String TAG = "TaskView";
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;
	private Task task;
	private Handler handler;
	private int tasksType = -1;
	private TaskListItemAdapter listViewAdapter;

	public TaskView(Context paramContext, int type) {
		super(paramContext);
		tasksType = type;
		dao = new TaskDao2(paramContext);
	}

	public TaskView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		dao = new TaskDao2(paramContext);
	}

	public TaskView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		dao = new TaskDao2(paramContext);
	}

	private void getData(int paramInt) {
		String type = "";
		switch (paramInt) {
		case TYPE_1:
			type = "type1";
			break;
		case TYPE_2:
			type = "type2";
			break;
		case TYPE_3:
			type = "type3";
			break;
		case TYPE_4:
			type = "type4";
			break;
		default:
			break;
		}
		new ContentTask().execute(type);
	}

	/**
	 * 顫恦iew
	 */
	private void initViews() {
		inflate(getContext(), R.layout.task_info, this);
		((TextView) findViewById(R.id.tasks_title))
				.setText("" + this.tasksType);
		((TextView) findViewById(R.id.tasks_count)).setText("");

		ActionSlideExpandableListView localActionSlideExpandableListView = (ActionSlideExpandableListView) findViewById(R.id.task_lv);
		listViewAdapter = new TaskListItemAdapter();
		localActionSlideExpandableListView.setAdapter(listViewAdapter);

		localActionSlideExpandableListView.setOnItemClickListener(this);
		int[] buttonIds = new int[4];
		buttonIds[0] = R.id.buttonA;
		buttonIds[1] = R.id.buttonB;
		buttonIds[2] = R.id.buttonC;
		buttonIds[3] = R.id.TextA;
		localActionSlideExpandableListView.setItemActionListener(listener,
				buttonIds);
		getData(tasksType);
	}

	//
	private void parseData() {
	}

	// 鐠佸墽鐤嗛弰鍓с仛閸婏拷
	private void setValue() {
	}

	/**
	 * 
	 * @param paramInt
	 * @param paramTask
	 * @param paramHandler
	 */
	public void initViews(int paramInt, Task paramTask, Handler paramHandler) {
		this.tasksType = paramInt;
		this.task = paramTask;
		this.handler = paramHandler;
		initViews();
		switch (paramInt) {
		case 1:
		case 2:
		case 3:
		}
	}

	public void onClick(View paramView) {
		L.d(TAG, "paramView" + paramView.getId());
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		L.i(TAG, "__");
	}

	public void setTasksType(int paramInt) {
		this.tasksType = paramInt;
	}

	// //////////////////////////
	// 
	// //////////////////////////

	public OnActionClickListener listener = new OnActionClickListener() {

		@Override
		public void onClick(View itemView, View clickedView, int position) {
			// TODO Auto-generated method stub
			switch (clickedView.getId()) {
			case R.id.buttonA:
		
				L.i(TAG, "buttonB");
				Toast.makeText(getContext(), "buttonB", 0).show();
				L.i(TAG, "buttonA");
				Intent intent2 = new Intent(getContext(),RemindActivity.class);
				Task task2 = listViewAdapter.getItem(position);
				getContext().startActivity(intent2);
				break;
			case R.id.buttonB:
				L.i(TAG, "buttonB");
				Toast.makeText(getContext(), "ring", 0).show();
				L.i(TAG, "buttonA");
				Intent intent = new Intent(getContext(),AlarmAlertActivity.class);
				Task task = listViewAdapter.getItem(position);
				intent.putExtra(AlarmAlertActivity.TASK_KEY,task.getId());
				
				getContext().startActivity(intent);
				break;
			case R.id.buttonC:
				L.i(TAG, "buttonC");
				Toast.makeText(getContext(), "buttonC", 0).show();
				break;
			case R.id.TextA:
				L.i(TAG, "TextA");
				Toast.makeText(getContext(), "TextA", 0).show();
				break;

			default:
				break;
			}

		}
	};
	private TaskDao2 dao;

	// /////////////////////
	//
	// /////////////////////
	public class ContentTask extends AsyncTask<String, Void, List<Task>> {

		@Override
		protected List<Task> doInBackground(String... params) {

			return getBitmapInfo(params[0]);
		}

		@Override
		protected void onPostExecute(List<Task> result) {
			// TODO
			super.onPostExecute(result);
			listViewAdapter.add(result);
			listViewAdapter.notifyDataSetChanged();
		}

		/**
		 * 
		 * @return
		 */
		private List<Task> getBitmapInfo(String type) {
			List<Task> tasks = dao.findTaskAll(type);
			return tasks;
		}
	}
}