package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.adapter.TaskListItemAdapter;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.model.TaskInfo;
import com.esirong.timer.view.TaskView;

/**
 * 
 * @author esirong
 * 
 */
public class MainActivity extends Activity implements OnClickListener,
        OnPageChangeListener {
    private static final String TAG = "MainActivity";
    private Button userCenter;//
    private List<View> viewList;//
    private ViewPager viewpager;
    private TaskListItemAdapter listViewAdapter;
    private TaskDao2 dao;

    List<Fragment> fragmentList = new ArrayList<Fragment>();

    // ////////////////////////
    // viewPager
    // ///////////////////////
    PagerAdapter adapter = new PagerAdapter() {
        public void destroyItem(ViewGroup paramViewGroup, int paramInt,
                Object paramObject) {
            paramViewGroup.removeView((View) MainActivity.this.viewList
                    .get(paramInt));
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
            paramViewGroup.addView(
                    (View) MainActivity.this.viewList.get(paramInt), 0);
            return MainActivity.this.viewList.get(paramInt);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    };

    // end

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//
        dao = new TaskDao2(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
        setListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewpager.setAdapter(adapter);
        initViewPager();
        getData(null);
    }

    /**
	 * 
	 */
    private void initViewPager() {
        this.viewpager = ((ViewPager) findViewById(R.id.viewpager));
        TaskView localTaskView1 = new TaskView(this, TaskView.TYPE_1);
        localTaskView1.initViews(1, null, null);
        TaskView localTaskView2 = new TaskView(this, TaskView.TYPE_2);
        localTaskView2.initViews(2, null, null);
        TaskView localTaskView3 = new TaskView(this, TaskView.TYPE_3);
        localTaskView3.initViews(3, null, null);
        TaskView localTaskView4 = new TaskView(this, TaskView.TYPE_4);
        localTaskView4.initViews(4, null, null);

        this.viewList = new ArrayList();
        viewList.add(localTaskView1);
        viewList.add(localTaskView2);
        viewList.add(localTaskView3);
        viewList.add(localTaskView4);

        viewpager.setAdapter(adapter);
        listViewAdapter = new TaskListItemAdapter();

    }

    /**
	 *
	 */
    private void initView() {
        this.userCenter = ((Button) findViewById(R.id.btn_user_center));

    }

    /**
	 * 
	 */
    private void setListener() {
        userCenter.setOnClickListener(this);
        viewpager.setOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "" + v.getId());
        if (v.getId() == R.id.btn_user_center) {
            Intent intent = new Intent();
            intent.setClass(this, TaskEditActivity.class);
            startActivity(intent);
        }

    }

    /**
	 * 
	 */
    private void getData(String str) {
        new ContentTask().execute("url");
    }

    // ////////////////////////
    //
    // ////////////////////////
    public class ContentTask extends AsyncTask<String, Void, List<Task>> {

        @Override
        protected List<Task> doInBackground(String... params) {

            return getBitmapInfo();
        }

        @Override
        protected void onPostExecute(List<Task> result) {
            super.onPostExecute(result);
            listViewAdapter.add(result);
            listViewAdapter.notifyDataSetChanged();
        }

        /**
         * 
         * @return
         */
        private List<Task> getBitmapInfo() {
            List<TaskInfo> list = new ArrayList<TaskInfo>();
            List<Task> tasks = dao.findTaskAll();
            return tasks;
        }

    }

    // //////////////////////////
    //
    // //////////////////////////
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

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }
}
