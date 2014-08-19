package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.model.Reminder;

/**
 * 提醒设置操作界面
 * 
 * @author esirong
 * 
 */
public class RemindActivity extends FragmentActivity implements
		OnItemClickListener {

	private static final String TASK_KEY = "taskid";
	private Task task;
	private GridView gridView;
	private RemindAdapter adapter;
	private int selected = 1;
	private TaskDao2 dao;
	private TextView title;
	private ArrayList<Reminder> list = new ArrayList<Reminder>();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_remind);
		dao = new TaskDao2(getApplicationContext());

		initView();
		initItntent();
		if (task != null) {
			title = (TextView) findViewById(R.id.task_title);
			title.setText(task.getTitle());
		}
		adapter = new RemindAdapter();
		List list = initItems();
		adapter.setRemiderItem(list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);

	}

	private void initItntent() {
		Intent intent = getIntent();
		if (intent != null) {
			Long taskId = intent.getLongExtra(TASK_KEY, -1);
			task = dao.findTask(taskId);
		}

	}

	private List initItems() {
		
		list.add(new Reminder("准时"));
		list.add(new Reminder("提前5分钟"));
		list.add(new Reminder("提前10分钟"));
		list.add(new Reminder("提前15分钟"));
		list.add(new Reminder("提前30分钟"));
		list.add(new Reminder("提前1小时"));
		list.add(new Reminder("自定义"));
		return list;
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);

	}

	class RemindAdapter extends BaseAdapter {

		private List<Reminder> itemsList;

		@Override
		public int getCount() {
			return itemsList.size();
		}

		public void setRemiderItem(List list) {
			itemsList = list;

		}

		@Override
		public Reminder getItem(int position) {
			return itemsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Reminder reminder = (Reminder) itemsList.get(position);
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = parent.inflate(getApplicationContext(),
						R.layout.reminder_item, null);

				holder.reminder_text = (TextView) convertView
						.findViewById(R.id.reminder_text);
				convertView.setTag(holder);
			} else {
				// 取出holder
				holder = (ViewHolder) convertView.getTag();
			}
			holder.reminder_text.setText(reminder.getName());
			if(reminder.isSelected()){
				convertView.setBackgroundColor(Color.RED);
			}else{
				convertView.setBackgroundResource(R.drawable.reminder_item_bg_card_color_selector);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		TextView reminder_text;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int postion,
			long arg3) {
		Reminder reminder = adapter.getItem(postion);
		reminder.setSelected(!reminder.isSelected());
		
		adapter.notifyDataSetChanged();

	}
	@Override
	protected void onDestroy() {
		Intent data = new Intent();
		ArrayList<String> value = new ArrayList<String>();
		setResult(Activity.RESULT_OK, data);
		super.onDestroy();
	}
	
}
