package com.esirong.timer.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.esirong.timer.Address;
import com.esirong.timer.R;

public class ContextAdapter extends BaseAdapter {
	private Context context;
	private List<Address> userList;
	HashMap<String, Boolean> states = new HashMap<String, Boolean>();

	public ContextAdapter(Context context, List<Address> userList) {
		this.context = context;
		this.userList = userList;
	}

	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listvview_item_signal, null);
			holder = new ViewHolder();
			holder.background = (LinearLayout) convertView
					.findViewById(R.id.search_user_list_item);
			holder.userName = (TextView) convertView
					.findViewById(R.id.search_user_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final RadioButton radio = (RadioButton) convertView
				.findViewById(R.id.radio_btn);
		holder.rdBtn = radio;

		holder.userName.setText(userList.get(position).getName());
		// 根据Item位置分配不同背景
//		if (userList.size() > 0) {
//			if (userList.size() == 1) {
//				holder.background
//						.setBackgroundResource(R.drawable.more_item_press);
//			} else {
//				if (position == 0) {
//					holder.background
//							.setBackgroundResource(R.drawable.more_itemtop_press);
//				} else if (position == userList.size() - 1) {
//					holder.background
//							.setBackgroundResource(R.drawable.more_itembottom_press);
//				} else {
//					holder.background
//							.setBackgroundResource(R.drawable.more_itemmiddle_press);
//				}
//			}
//		}
		// 当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
		holder.rdBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// 重置，确保最多只有一项被选中
				for (String key : states.keySet()) {
					states.put(key, false);

				}
				states.put(userList.get(position).getName(), radio.isChecked());
				ContextAdapter.this.notifyDataSetChanged();
			}
		});

		boolean res = false;
		if (states.get(userList.get(position).getName()) == null
				|| states.get(userList.get(position).getName()) == false) {
			res = false;
			states.put(userList.get(position).getName(), false);
		} else
			res = true;

		holder.rdBtn.setChecked(res);

		return convertView;
	}

	static class ViewHolder {
		LinearLayout background;
		TextView userName;
		RadioButton rdBtn;
	}
public HashMap<String, Boolean> getStatus(){
	return states;
}
public void addList(Address address) {
	userList.add(0, address);
}
}
