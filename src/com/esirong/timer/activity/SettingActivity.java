package com.esirong.timer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SettingActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getFragmentManager().beginTransaction().replace(android.R.id.content,  
	                new SettingFragment()).commit();  
	}
}
