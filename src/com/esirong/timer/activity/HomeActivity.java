package com.esirong.timer.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.R;
import com.esirong.timer.TaskDao;

/*
 * 1正在做什么
 * 分别回答	
 清空	我该做什么
 检视	我正在做什么
 总结	我做了什么
 计划	我将要做什么
 */
public class HomeActivity extends FragmentActivity implements OnClickListener {
	private SQLiteDatabase db;

	private EditText editText;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private TaskDao taskDao;

	private Cursor cursor;

	private View viewButton;
	private View settingButton;
	private View currentTaskPanel;
	private View typeTaskPanel1;
	private View typeTaskPanel2;
	private View typeTaskPanel3;
	private View typeTaskPanel4;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_home);
		initView();
		initData();
		setOnListener();

		// 我该做什么
		/*
		 * 当天，当前的任务
		 */
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"notes-db", null);
		db = helper.getReadableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		taskDao = daoSession.getTaskDao();
		//查询条件，未过期，的第一个
		//排序，选择第一个
//		String where ="start_at > now&&start_at<今天";
//		taskDao.queryRaw(where, "","","","");

		//我正在做什么
		//查询：start at<now &&end_at>now
		//就是正在进行事务了
		
//		where = "";
		//我做了什么，即已经结束的事务
		//end_at<now
		
		//做将要做什么
		//start>now，且大于一定数据，如一天，一小时，的缓冲时段。
		
		//包装这四类操作。并获取数据的方当法，可复用
		
	}
	private void initView() {
		viewButton = findViewById(R.id.go_head);
		settingButton= findViewById(R.id.go_setting);
		currentTaskPanel = findViewById(R.id.current_task_panel);
		typeTaskPanel1 = findViewById(R.id.type1_task_panel);
		typeTaskPanel2 = findViewById(R.id.type2_task_panel);
		typeTaskPanel3 = findViewById(R.id.type3_task_panel);
		typeTaskPanel4 = findViewById(R.id.type4_task_panel);
		
	}
	private void initData() {
		
		
	}
	private void setOnListener() {
		// TODO Auto-generated method stub
		currentTaskPanel.setOnClickListener(this);
		typeTaskPanel1.setOnClickListener(this);
		typeTaskPanel2.setOnClickListener(this);
		typeTaskPanel3.setOnClickListener(this);
		typeTaskPanel4.setOnClickListener(this);
		viewButton.setOnClickListener(this);
		settingButton.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.go_head:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.go_setting:
			Intent intent2 = new Intent(this, SettingActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
		
	}

}
