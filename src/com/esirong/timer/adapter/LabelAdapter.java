package com.esirong.timer.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.esirong.timer.Label;
import com.esirong.timer.R;

public class LabelAdapter extends BaseAdapter {

	// 填充数据的list
	private List<Label> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<String, Boolean> isSelected;
	// 上下文
	private Context context;
	// 用来导入布局
	private LayoutInflater inflater = null;

	// 构造器
	public LabelAdapter(List<Label> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<String, Boolean>();

		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(list.get(i).getName(), false);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Label getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// 获得ViewHolder对象
			holder = new ViewHolder();
			// 导入布局并赋值给convertview
			convertView = inflater.inflate(R.layout.listviewitem, null);
			holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			// 为view设置标签
			convertView.setTag(holder);
		} else {
			// 取出holder
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置list中TextView的显示
		holder.tv.setText(list.get(position).getName());
		// 根据isSelected来设置checkbox的选中状况
		holder.cb.setChecked(getIsSelected().get(list.get(position).getName()));
		// holder.cb.setChecked(true);
		return convertView;
	}

	public static HashMap<String, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<String, Boolean> isSelected) {
		LabelAdapter.isSelected = isSelected;
	}

	public void setList(List<Label> list) {
		this.list = list;
	}
	public void addList(Label label) {
		list.add(0, label);
	}

	public static class ViewHolder {
		public TextView tv;
		public CheckBox cb;
	}

	public Object getSelectedItem() {
		return null;
	}
}
