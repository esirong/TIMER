package com.esirong.timer.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.esirong.timer.R;

/**
 * VIEWPAGE��page ÿһ��viewpager�����ݡ��������pager����
 * 
 * @author esirong
 * 
 */
public class ViewPagerFragment extends Fragment {

	public static ViewPagerFragment newInstance(String s) {
		return new ViewPagerFragment();
		// ��ݼ���
	}

	public ViewPagerFragment() {
		super();
	}

	/**
	 * ���Ǵ˺�����ͨ��inflater inflate����õ�view��󷵻�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.task_info, container, false);
		TextView tv = (TextView) view.findViewById(R.id.tasks_title);
		tv.setText("����");

		Bundle bundle = getArguments();
		int upImageId = bundle.getInt("upImageId");
		String text = bundle.getString("text");

		return view;
	}

}
