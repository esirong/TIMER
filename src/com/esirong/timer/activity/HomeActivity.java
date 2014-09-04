package com.esirong.timer.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.TaskDao;
import com.esirong.timer.db.SummaryDao;
import com.esirong.timer.db.SummaryImpl;
import com.esirong.timer.util.Toasts;


public class HomeActivity extends FragmentActivity implements OnClickListener {
	private SQLiteDatabase db;

	private EditText editText;

	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private TaskDao taskDao;
	private  SummaryDao dao;

	private Cursor cursor;
	private Task mTask;
	//当前事务操作按钮
	private Button btn_start;
	private Button btn_delay;
	private Button btn_close;
	private Button btn_done;
	
	private View viewButton;
	private View settingButton;
	private View currentTaskPanel;
	private View typeTaskPanel1;
	private View typeTaskPanel2;
	private View typeTaskPanel3;
	private View typeTaskPanel4;
	
	
	@Override
	protected void onCreate(Bundle bundle) {
	    super.onCreate(bundle);
        setContentView(R.layout.activity_home);
        dao = new SummaryImpl();
        initActionBar();
        initView();
        initData();
        setOnListener();

        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"notes-db", null);
        db = helper.getReadableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        taskDao = daoSession.getTaskDao();
	}
	
	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		
	}
	private void initView() {
		viewButton = findViewById(R.id.go_head);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_delay = (Button) findViewById(R.id.btn_delay);
		btn_close = (Button) findViewById(R.id.btn_close);
		btn_done = (Button) findViewById(R.id.btn_done);
		settingButton= findViewById(R.id.go_setting);
		currentTaskPanel = findViewById(R.id.current_task_panel);
		typeTaskPanel1 = findViewById(R.id.type1_task_panel);
		typeTaskPanel2 = findViewById(R.id.type2_task_panel);
		typeTaskPanel3 = findViewById(R.id.type3_task_panel);
		typeTaskPanel4 = findViewById(R.id.type4_task_panel);
		
	}
	private void initData() {
		//初始数据
	int done = dao.getCount4Done();
	int undone = dao.getCount4Undone();
	double efficient = 	dao.getEfficientPercent();
	int total = dao.getTotals();
	mTask = dao.getCurrentTask();
	mTask = new Task();
	TextView doneText = (TextView) currentTaskPanel.findViewById(R.id.done_percent);
	TextView efficientText = (TextView) currentTaskPanel.findViewById(R.id.efficient_percent);
	doneText.setText("完成："+done+"/"+total);	
	efficientText.setText("效率指数:"+efficient*100+"%");
	}
	private void setOnListener() {
		// TODO Auto-generated method stub
		btn_start.setOnClickListener(this);
		btn_delay.setOnClickListener(this);
		btn_close.setOnClickListener(this);
		btn_done.setOnClickListener(this);
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
		case R.id.btn_start:
			//这件处理完成则。则提示下一事务（在30分钟内到期的）
			Toasts.showToastShort(getApplicationContext(), "btn_start");
			break;
		case R.id.btn_delay:
			//这件处理完成则。则提示下一事务（在30分钟内到期的）
			Toasts.showToastShort(getApplicationContext(), "btn_delay");
			break;
		case R.id.btn_close:
			Toasts.showToastShort(getApplicationContext(), "btn_close");
			//这件处理完成则。则提示下一事务（在30分钟内到期的）
			break;
		case R.id.btn_done:
			//这件处理完成则。则提示下一事务（在30分钟内到期的）
			Toasts.showToastShort(getApplicationContext(), "btn_done");
			break;
		case R.id.go_head:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.go_setting:
			Intent intent2 = new Intent(this, SettingActivity.class);
			startActivity(intent2);
			break;
		case R.id.current_task_panel:
			Toasts.showToastShort(getApplicationContext(), "currentTaskPanel");
			break;
		case R.id.type1_task_panel:
			Toasts.showToastShort(getApplicationContext(), "type1_task_panel");
			break;
		case R.id.type2_task_panel:
			Toasts.showToastShort(getApplicationContext(), "type2_task_panel");
			break;
		case R.id.type3_task_panel:
			Toasts.showToastShort(getApplicationContext(), "type3_task_panel");
			break;
		case R.id.type4_task_panel:
			Toasts.showToastShort(getApplicationContext(), "type4_task_panel");
			break;
		default:
			break;
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.edit_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			// openSearch();
			return true;
		case R.id.action_add:
			Toasts.showToastShort(this, "add_task");
			startActivity(new Intent(this, TaskEditActivity.class));
			return true;
		case R.id.action_settings:
			Toasts.showToastShort(this, "action_settings");
			Intent intent = new Intent(this, SettingActivity.class);
			startActivity(intent);
			return true;
		case android.R.id.home:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
