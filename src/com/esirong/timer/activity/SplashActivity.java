package com.esirong.timer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.esirong.timer.R;
import com.esirong.timer.util.AppUpdateUtil;
import com.esirong.timer.util.L;
import com.esirong.timer.util.NetWorkUtil;
import com.esirong.timer.util.VersionUtil;

/**
 * 启动界面
 * 
 * @author esirong@qq.com
 * 
 */
@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	private final int SPLASH_DELAY_MILLIS = 2000;
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private static final int GO_MAIN = 0;
	private static final int GO_GUIDE = 1;
	private boolean isFirstIn;

	private TextView mVersionNameText;

	//
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_MAIN:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		init();
		// 初始化数据
		initData();
		// 检查网络
		checkNetWork();
		// 检查版本
		checkVer();

	}

	private void checkVer() {
		if (!NetWorkUtil.isNetworkConnected(getApplicationContext())) {
			return;
		}
		/*
		 * 获取下载信息 比较版本信息 提示是否下载 是：开启下载线程 否:不作处理
		 */
		// 版本升级开启一个子线程来处理
		new Thread() {

			@Override
			public void run() {
				AppUpdateUtil.checkForUdpate(getApplicationContext());
			}

		}.start();

		L.d(TAG, "检查版本");
	}

	/**
	 * 检测网络
	 */
	private void checkNetWork() {
		// TODO
		if (!NetWorkUtil.isNetworkConnected(getApplicationContext())) {
			Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
		}
		L.d(TAG, "检测网络");

	}

	private void initData() {
		L.d(TAG, "初始化数据");

	}

	private void init() {
		mVersionNameText = (TextView) findViewById(R.id.version_name);
		mVersionNameText.setText(VersionUtil.getCurrentVersionName(this));
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.anim_transparent_loop);
		findViewById(R.id.app_icon).startAnimation(animation);

		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (isFirstIn) {
			handler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		} else {
			handler.sendEmptyMessageDelayed(GO_MAIN, SPLASH_DELAY_MILLIS);
		}
	}

	/**
	 * 主页
	 */
	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
		overridePendingTransition(R.anim.anim_zoom_in, R.anim.anim_zoom_out);
	}

	/**
	 * 引导页
	 */
	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
}
