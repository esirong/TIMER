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

import com.esirong.timer.Address;
import com.esirong.timer.Label;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.Task_Label;
import com.esirong.timer.adapter.ContextAdapter;
import com.esirong.timer.adapter.LabelAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.util.Strings;
import com.esirong.timer.util.Toasts;

public class LocationDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context mContext;
	private TaskDao2 dao;

	public LocationDialog(Context context) {

		super(context);
		this.mContext = context;
		dao = new TaskDao2(context);
		init();

	}

	private void init() {
		setContentView(R.layout.todo_location_dialog2);
		setTitle("地点");
	}

	/** 所有列表项 */
	private ArrayList<Label> list = new ArrayList<Label>();
	/** 被先中列表项 */
	private static HashMap<String, Boolean> isLabelSelected = new HashMap<>();
	private TextView textview;
	private EditText add_new;
	private ContextAdapter adapter;
	private Task mTask;

	public void initDialog(ArrayList<Address> list, Task mTask) {
		setContentView(R.layout.todo_location_dialog2);
		ListView listview = (ListView) findViewById(R.id.list2);
		textview = (TextView) findViewById(R.id.textview);
		add_new = (EditText) findViewById(R.id.add_new);
		this.mTask = mTask;
		adapter = new ContextAdapter(mContext, list);

		if (mTask != null) {
			HashMap<String, Boolean> selectedMap = adapter.getStatus();
			for (String key : selectedMap.keySet()) {
				selectedMap.put(key, false);
			}
			String str = mTask.getAddress();
			if (str != null) {
				Address address = dao.findAddress(str);
				if (address != null) {
					selectedMap.put(address.getName(), true);
				}
			}
		}

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

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
			// 根据RadioButton的选择情况确定用户名
			HashMap<String, Boolean> selected = adapter.getStatus();
			for (Entry<String, Boolean> entity : selected.entrySet()) {
				if (entity.getValue()) {
					String str = entity.getKey();
					Address address = dao.findAddress(str);
					mTask.setAddress(str);
					dao.insertTask(mTask);
				}

			}
			cancel();
		} else if (v.getId() == R.id.edit_btn) {
			String str = add_new.getText().toString();
			// 保存
			if (Strings.isEmptyOrNull(str)) {
				return;
			}
			Address address = null;
			address = dao.findAddress(str);
			if (address == null) {
				address = new Address();
				address.setName(str);
				dao.insertAddress(address);
				adapter.addList(address);
			} else {

			}

			HashMap<String, Boolean> selectedMap = adapter.getStatus();
			if (selectedMap.containsKey(address.getName())) {
			}
			for (String key : selectedMap.keySet()) {
				selectedMap.put(key, false);
			}
			selectedMap.put(address.getName(), true);
			adapter.notifyDataSetChanged();

		} else if (v.getId() == R.id.cancel_bt) {
			dismiss();
		}

	}
}
