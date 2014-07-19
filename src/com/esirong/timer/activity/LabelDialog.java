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

import com.esirong.timer.Label;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.Task_Label;
import com.esirong.timer.adapter.LabelAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;

public class LabelDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context mContext;
	private TaskDao2 dao;

	public LabelDialog(Context context) {

		super(context);
		this.mContext = context;
		dao = new TaskDao2(context);
		init();

	}

	private void init() {
		setContentView(R.layout.todo_location_dialog);
		setTitle("目标");
		ListView listview = (ListView) findViewById(R.id.list);
		final LabelAdapter adapter = new LabelAdapter(list, mContext);
		listview.setAdapter(adapter);
	}

	/** 所有列表项 */
	private ArrayList<Label> list = new ArrayList<Label>();
	/** 被先中列表项 */
	private static HashMap<String, Boolean> isLabelSelected = new HashMap<>();
	private TextView textview;
	private EditText add_new;
	private LabelAdapter adapter;
	private Task mTask;

	public void initDialog(ArrayList<Label> list, Task mTask) {
		setContentView(R.layout.todo_location_dialog);
		ListView listview = (ListView) findViewById(R.id.list);
		textview = (TextView) findViewById(R.id.textview);
		add_new = (EditText) findViewById(R.id.add_new);
		this.mTask = mTask;

		adapter = new LabelAdapter(list, mContext);

		for (Label l : list) {
			isLabelSelected.put(l.getName(), false);
			if (mTask != null) {
				Task_Label task_Label = dao
						.findTaskLabel(mTask.getId(), l.getId());
				if (task_Label != null) {
					Toasts.showToastShort(mContext, task_Label+":"+mTask.getId()+":"+ l.getId());
					isLabelSelected.put(l.getName(), true);
				}
			}

		}

		if (isLabelSelected != null) {
			LabelAdapter.setIsSelected(isLabelSelected);
		}
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				com.esirong.timer.adapter.LabelAdapter.ViewHolder holder = (com.esirong.timer.adapter.LabelAdapter.ViewHolder) view
						.getTag();
				holder.cb.toggle();

				String name = holder.tv.getText().toString();
				LabelAdapter.getIsSelected().put(name, holder.cb.isChecked());

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

	public void setDataInfo(ArrayList<Label> list) {

		this.list = list;
	}

	public void setSelected() {

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.ok_bt) {

			HashMap<String, Boolean> map = LabelAdapter.getIsSelected();
			for (Entry<String, Boolean> set : map.entrySet()) {
				if (set.getValue()) {
					Task_Label entity = null;
					Label Label = dao.findLabel(set.getKey());
					entity = dao.findTaskLabel(mTask.getId(), Label.getId());
					if (entity != null) {
						dao.insertTaskLabel(entity);
					} else {
						entity = new Task_Label();
						entity.setTaskId(mTask.getId());
						entity.setLabelId(Label.getId());
						dao.insertTaskLabel(entity);
					}

					Toasts.showToastShort(mContext, entity +":"+mTask.getId().toString()+":"+Label.getId()+ "");
				} else {
					Task_Label entity = null;
					Label Label = dao.findLabel(set.getKey());
					entity = dao.findTaskLabel(mTask.getId(), Label.getId());
					if (entity != null) { 
						dao.deleteTaskLabel(entity);
						Toasts.showToastShort(mContext, entity + "");
					} else {
					}
					
				}

			}
			cancel();
		} else if (v.getId() == R.id.edit_btn) {
			String str = add_new.getText().toString();
			if (!Strings.isBlank(str)) {
				add_new.setText("");
				
				Label Label = null;
				Label= dao.findLabel(str);
				if(Label ==null){
					Label = new Label();
					Label.setName(str);
					long id = dao.insertLabel(Label);
					adapter.addList(Label);
					adapter.getIsSelected().put(str, true);
				}
				
				adapter.notifyDataSetChanged();
			}

		} else if (v.getId() == R.id.cancel_bt) {
			dismiss();
		}

	}
}
