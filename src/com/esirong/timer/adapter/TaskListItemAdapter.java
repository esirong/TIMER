package com.esirong.timer.adapter;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esirong.timer.R;
import com.esirong.timer.Task;

/**
 * 条目适配器
 * 
 * @author esirong
 * 
 */
public class TaskListItemAdapter extends BaseAdapter {
	private LinkedList<Task> mInfos;

	public TaskListItemAdapter() {
		mInfos = new LinkedList<Task>();
	}

	public void add(Task info) {
		mInfos.add(info);
	}

	public void add(List<Task> infos) {
		mInfos.addAll(infos);
	}

	/**
	 * 加在在队列前
	 * 
	 * @param datas
	 */
	public void addItemTop(List<Task> infos) {
		for (Task info : infos) {
			mInfos.addFirst(info);
		}
	}

	@Override
	public int getCount() {
		return mInfos.size();
	}

	@Override
	public Task getItem(int position) {
		return mInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		Task info = mInfos.get(position);
		if (convertView == null) {

			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			convertView = layoutInflator.inflate(R.layout.task_info_list_item,
					null);

			holder = new ViewHolder();
			holder.taskName = (TextView) convertView
					.findViewById(R.id.TaskName);
			holder.taskStatus = (TextView) convertView
					.findViewById(R.id.TaskStatus);
			holder.taskTime = (TextView) convertView
					.findViewById(R.id.TaskTime);
			holder.partnerName = (TextView) convertView
					.findViewById(R.id.PartnerName);
			holder.avatarIcon = (ImageView) convertView
					.findViewById(R.id.AvatarIcon);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		holder.taskName.setText("" + info.getTitle());

		// 事务状态
		holder.taskStatus.setText(initTaskStatus(info.getStatus()));

		holder.taskTime.setText("" + updateTitle(info.getStart_at()));

		holder.partnerName.setText("" + info.getType());

		// 异步加载图片
		holder.avatarIcon.setImageResource(initTaskType(info.getType()));

		return convertView;
	}

	//务类型
	private int initTaskType(String type) {
		int result = R.drawable.type1;
		if (type.equals("type1")) {
			result = R.drawable.type1;
		} else if (type.equals("type2")) {
			result = R.drawable.type2;
		} else if (type.equals("type3")) {
			result = R.drawable.type3;
		} else if (type.equals("type4")) {
			result = R.drawable.type4;
		} else {
			result = R.drawable.type1;
		}
		return result;
	}

	private String initTaskStatus(Integer status) {
		// 完成，未完成,进行中，暂停中。被取消
		String statusStr = "";
		switch (status) {
		case 0:
			statusStr = "未开始";
			break;
		case 1:
			statusStr = "进行中";
			break;
		case 2:
			statusStr = "结束";
			break;
		case 3:
			statusStr = "暂停";
			break;
		case 4:
			statusStr = "取消";
			break;
		default:
			break;
		}
		return statusStr;

	}

	private static class ViewHolder {
		TextView taskName;
		TextView taskStatus;
		TextView taskTime;
		TextView partnerName;
		ImageView avatarIcon;

	}

	private String updateTitle(long date) {
		int flag = DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_SHOW_TIME;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH：mm");

		return format.format(date);
	}
}
