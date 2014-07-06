package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.esirong.timer.R;

/**
 * @author esirong
 *
 */
public class GuideActivity extends Activity implements OnPageChangeListener{
	
	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	private ViewPager viewpager;
	private List<View> views;
	// �ײ�С��ͼƬ  
    private ImageView[] dots;  
    // ��¼��ǰѡ��λ��  
    private int currentIndex; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//ȥ�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);   //
		setContentView(R.layout.activity_guide);
		// ��ʼ��ҳ��  
        initViews();  
        // ��ʼ���ײ�С��  
        initDots();
        //���ü���
        setListener();
		
	}

	private void setListener() {
		
	}

	/**
	 * ��ʼ�ײ�С��
	 */
	private void initDots() {
		// TODO Auto-generated method stub
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		
		dots = new ImageView[views.size()];
		for(int i=0; i<views.size(); i++){
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);
		}
		
		currentIndex = 0;  
        dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬  
        
	}
	
	/**
	 * ���õ�ǰ��
	 * @param position
	 */
	private void setCurrentDot(int position){
		if(position<0 || position>views.size()-1){
			return;
		}
		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);
		currentIndex = position;
	}

	/**
	 * ��ʼ��ҳ
	 */
	private void initViews() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		// ��ʼ����ͼƬ�б�  
        views.add(inflater.inflate(R.layout.what_new_one, null));  
        views.add(inflater.inflate(R.layout.what_new_two, null));  
        views.add(inflater.inflate(R.layout.what_new_three, null));  
        views.add(inflater.inflate(R.layout.what_new_four, null));  
		
        // ��ʼ��Adapter  
        viewpager = (ViewPager) findViewById(R.id.viewpager);  
        viewpager.setAdapter(new MyPagerAdapter()); 
        viewpager.setOnPageChangeListener(this);
		
	}

	//view����================================================
	
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if(views!=null){
				return views.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}
		
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(views.get(position));
			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position), 0);
			if(position == views.size() -1){
				//�����ҳ
				 ImageView mStartImageButton = (ImageView) container
		                    .findViewById(R.id.iv_start);
		            mStartImageButton.setOnClickListener(new OnClickListener() {

		                @Override
		                public void onClick(View v) {
		                    // �����Ѿ���
		                    setGuided();
		                    goMain();
		                }

		            });
			}
			
			return views.get(position);
		}
		
	};
	
	
	 private void setGuided() {
	        SharedPreferences preferences = getSharedPreferences(
	                SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
	        Editor editor = preferences.edit();
	        // �������
	        editor.putBoolean("isFirstIn", false);
	        // �ύ�޸�
	        editor.commit();
	    }
	 
	 private void goMain() {
	        // ��ת
	        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
	        GuideActivity.this.startActivity(intent);
	        GuideActivity.this.finish();
	    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//==ҳ��ı����===================================================	
	// ������״̬�ı�ʱ����
	
	

}
