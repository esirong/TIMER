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
 * ��Ŀ������
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
	 * �����ڶ���ǰ
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
	public Object getItem(int position) {
		return mInfos.get(position-1);
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
			
			LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
			convertView = layoutInflator.inflate(R.layout.task_info_list_item, null);
			
			holder = new ViewHolder();
			holder.taskName = (TextView) convertView.findViewById(R.id.TaskName);
			holder.taskStatus = (TextView) convertView.findViewById(R.id.TaskStatus);
			holder.taskTime = (TextView) convertView.findViewById(R.id.TaskTime);
			holder.partnerName = (TextView) convertView.findViewById(R.id.PartnerName);
			holder.avatarIcon = (ImageView) convertView.findViewById(R.id.AvatarIcon);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();

		holder.taskName.setText("" + info.getTitle());

		holder.taskStatus.setText("����״̬" + info.getStatus());

		holder.taskTime.setText("����ʱ��" + updateTitle(info.getStart_at()));

		holder.partnerName.setText("�����ɫ" + info.getType());

		//�첽����ͼƬ
		// holder.avatarIcon.setImageResource(R.drawable.type1);

		return convertView;
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH��mm");

		return format.format(date);
	}
}
