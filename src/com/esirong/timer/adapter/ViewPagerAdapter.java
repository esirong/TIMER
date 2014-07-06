package com.esirong.timer.adapter;

import java.util.List;

import com.esirong.timer.model.TaskInfo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * viewpager������,Ϊ��ʾ�����������
 * 
 * @author esirong
 * 
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentList;
	
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void addViews(List<Fragment> fragmentList){
		this.fragmentList = fragmentList;
	}
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentList.get(arg0);
	}
	@Override
	public int getCount() {
		return fragmentList.size();
	}

	
}
