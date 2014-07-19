package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.esirong.timer.Goal;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.adapter.GoalAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;

public class GoalDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context mContext;
	private TaskDao2 dao;

	public GoalDialog(Context context) {

		super(context);
		this.mContext = context;
		dao = new TaskDao2(context);
		init();

	}

	private void init() {
		setContentView(R.layout.todo_location_dialog);
		setTitle("目标");
		ListView listview = (ListView) findViewById(R.id.list);
		final GoalAdapter adapter = new GoalAdapter(list, mContext);
		listview.setAdapter(adapter);
	}

	/** 所有列表项 */
	private ArrayList<Goal> list = new ArrayList<Goal>();
	/** 被先中列表项 */
	private static HashMap<String, Boolean> isGoalSelected = new HashMap<>();
	private TextView textview;
	private EditText add_new;
	private GoalAdapter adapter;
	private Task mTask;

	public void initDialog(ArrayList<Goal> list, Task mTask) {
		setContentView(R.layout.todo_location_dialog);
		ListView listview = (ListView) findViewById(R.id.list);
		textview = (TextView) findViewById(R.id.textview);
		add_new = (EditText) findViewById(R.id.add_new);
		this.mTask = mTask;

		adapter = new GoalAdapter(list, mContext);

		for (Goal l : list) {
			isGoalSelected.put(l.getName(), false);
			if (mTask != null) {
				Task_Goal task_Goal = dao
						.findTaskGoal(mTask.getId(), l.getId());
				if (task_Goal != null) {
					Toasts.showToastShort(mContext, task_Goal+":"+mTask.getId()+":"+ l.getId());
					isGoalSelected.put(l.getName(), true);
				}
			}

		}

		if (isGoalSelected != null) {
			GoalAdapter.setIsSelected(isGoalSelected);
		}
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				com.esirong.timer.adapter.GoalAdapter.ViewHolder holder = (com.esirong.timer.adapter.GoalAdapter.ViewHolder) view
						.getTag();
				holder.cb.toggle();

				String name = holder.tv.getText().toString();
				GoalAdapter.getIsSelected().put(name, holder.cb.isChecked());

			}
		});

		findViewById(R.id.ok_bt).setOnClickListener(this);
		findViewById(R.id.cancel_bt).setOnClickListener(this);
		findViewById(R.id.edit_btn).setOnClickListener(this);

	}

	@Override
	public void show() {
		super.show();
	}

	public void setDataInfo(ArrayList<Goal> list) {

		this.list = list;
	}

	public void setSelected() {

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.ok_bt) {

			HashMap<String, Boolean> map = GoalAdapter.getIsSelected();
			for (Entry<String, Boolean> set : map.entrySet()) {
				if (set.getValue()) {
					Task_Goal entity = null;
					Goal goal = dao.findGoal(set.getKey());
					entity = dao.findTaskGoal(mTask.getId(), goal.getId());
					if (entity != null) {
						dao.insertTaskGoal(entity);
					} else {
						entity = new Task_Goal();
						entity.setTaskId(mTask.getId());
						entity.setGoalId(goal.getId());
						dao.insertTaskGoal(entity);
					}

					Toasts.showToastShort(mContext, entity +":"+mTask.getId().toString()+":"+goal.getId()+ "");
				} else {
					Task_Goal entity = null;
					Goal goal = dao.findGoal(set.getKey());
					entity = dao.findTaskGoal(mTask.getId(), goal.getId());
					if (entity != null) {
						dao.deleteTaskGoal(entity);
						Toasts.showToastShort(mContext, entity + "");
					} else {
					}
					
				}

			}
			cancel();
		} else if (v.getId() == R.id.edit_btn) {
			String str = add_new.getText().toString();
			if (!Strings.isBlank(str)) {
				Goal goal = new Goal();
				goal.setName(str);
				long id = dao.insertGoal(goal);
				adapter.addList(goal);
				adapter.getIsSelected().put(str, true);
				adapter.notifyDataSetChanged();
			}

		} else if (v.getId() == R.id.cancel_bt) {
			dismiss();
		}

	}
}
