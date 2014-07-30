package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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

/**
 * 提醒设置操作界面
 * 
 * @author esirong
 * 
 */
public class RemindActivity extends FragmentActivity implements OnItemClickListener {
	
	private static final String TASK_KEY="taskid";
	private Task task;
	private GridView gridView;
	private RemindAdapter adapter;
	private TaskDao2 dao;
	private TextView title;
	

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_remind);
		dao = new TaskDao2(getApplicationContext());
		
		
		
		initView();
		initItntent();
		if(task != null){
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
		if(intent !=null){
			Long taskId = intent.getLongExtra(TASK_KEY, -1);
			task = dao.findTask(taskId);
		}
		
	}

	private List initItems() {
		ArrayList list = new ArrayList<>();
		list.add("准时");
		
		list.add("提前5分钟");
		list.add("提前10分钟");
		list.add("提前15分钟");
		list.add("提前30分钟");
		list.add("提前1小时");
		list.add("自定义");
		return list;
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);

	}

	class RemindAdapter extends BaseAdapter {

		private List itemsList;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemsList.size();
		}

		public void setRemiderItem(List list) {
			itemsList = list;

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup parent) {
			String str = (String) itemsList.get(arg0);
			View view = parent.inflate(getApplicationContext(),
					R.layout.reminder_item, null);

			TextView textview = (TextView) view
					.findViewById(R.id.reminder_text);
			textview.setText(str);
			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int postion, long arg3) {
		view.setSelected(!view.isSelected());
		adapter.notifyDataSetChanged();
		
		
		
	}
}
