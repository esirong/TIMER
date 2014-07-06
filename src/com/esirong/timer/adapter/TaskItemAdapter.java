package com.esirong.timer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esirong.timer.R;

public class TaskItemAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			
			LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
			convertView = layoutInflator.inflate(R.layout.task_info_list_item, null);
			
			holder = new ViewHolder();
			holder.taskName = (TextView) convertView.findViewById(R.id.TaskName);
			holder.avatarIcon = (ImageView) convertView.findViewById(R.id.AvatarIcon);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();

		holder.taskName.setText("�������" );


		//�첽����ͼƬ
		// holder.avatarIcon.setImageResource(R.drawable.type1);

		return convertView;
	}
	private static class ViewHolder {
		TextView taskName;
		ImageView avatarIcon;

	}
}
