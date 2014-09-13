package com.esirong.timer.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.esirong.timer.R;
import com.esirong.timer.Task;
import com.esirong.timer.db.TaskDao2;
import com.esirong.timer.model.Reminder;

/**
 * 提醒设置操作界面
 * 
 * @author esirong
 * 
 */
public class RemindActivity extends FragmentActivity implements
        OnItemClickListener, OnClickListener {

    /**
     * 传入事务ID
     */
    public static final String TASK_KEY = "taskid";
    /**
     * 事务
     */
    private Task task;
    private GridView gridView;
    /**
     * 保存按键
     */
    private Button saveButton;
    private RemindAdapter adapter;
    private TaskDao2 dao;
    private TextView title;
    private ArrayList<Reminder> list = new ArrayList<Reminder>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind);
        dao = new TaskDao2(getApplicationContext());

        initView();
        initItntent();
        if (task != null) {
            title = (TextView) findViewById(R.id.task_title);
            title.setText(task.getTitle());

        }
        adapter = new RemindAdapter();
        List<Reminder> list = initItems();
        adapter.setRemiderItem(list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    private void initItntent() {
        Intent intent = getIntent();
        if (intent != null) {
            long taskId = intent.getLongExtra(TASK_KEY, -1);
            task = dao.findTask(taskId);
        }

    }

    private List<Reminder> initItems() {

        list.add(new Reminder("准时", 0));
        list.add(new Reminder("提前5分钟", 5));
        list.add(new Reminder("提前10分钟", 10));
        list.add(new Reminder("提前15分钟", 15));
        list.add(new Reminder("提前30分钟", 30));
        list.add(new Reminder("提前1小时", 60));
        list.add(new Reminder("自定义", 0));
        initTaskAlertTime();
        return list;
    }

    private void initTaskAlertTime() {
        if (task == null) {
            return;
        }
        Long start = task.getStart_at();
        Long alert = task.getAlert_at();
        if (alert == null || start == null) {
            return;
        }
        long delay = alert - start;
        if (delay == 0) {
            list.get(0).setSelected(true);
        } else if (delay == 5 * 60 * 1000) {
            list.get(1).setSelected(true);
        } else if (delay == 10 * 60 * 1000) {
            list.get(2).setSelected(true);
        } else if (delay == 15 * 60 * 1000) {
            list.get(3).setSelected(true);
        } else if (delay == 30 * 60 * 1000) {
            list.get(4).setSelected(true);
        } else if (delay == 60 * 60 * 1000) {
            list.get(5).setSelected(true);
        } else {
            list.get(6).setSelected(true);
        }

    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        saveButton = (Button) findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

    }

    class RemindAdapter extends BaseAdapter {

        private List<Reminder> itemsList;

        @Override
        public int getCount() {
            return itemsList.size();
        }

        public void setRemiderItem(List<Reminder> list) {
            itemsList = list;

        }

        @Override
        public Reminder getItem(int position) {
            return itemsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Reminder reminder = itemsList.get(position);
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(),
                        R.layout.reminder_item, null);

                holder.reminder_text = (TextView) convertView
                        .findViewById(R.id.reminder_text);
                convertView.setTag(holder);
            } else {
                // 取出holder
                holder = (ViewHolder) convertView.getTag();
            }
            holder.reminder_text.setText(reminder.getName());
            if (reminder.isSelected()) {
                convertView.setSelected(true);
                convertView
                        .setBackgroundResource(R.drawable.btn_bg);
            } else {
                convertView.setSelected(false);
                convertView
                        .setBackgroundResource(R.drawable.reminder_item_bg_card_color_selector);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        TextView reminder_text;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int postion,
            long arg3) {
        Reminder reminder = adapter.getItem(postion);
        clearSelected();
        reminder.setSelected(!reminder.isSelected());
        if (reminder.isSelected()) {
            title.setText(reminder.getName());
        }

        adapter.notifyDataSetChanged();

    }

    private void clearSelected() {

        for (Reminder r : list) {
            r.setSelected(false);
        }
    }

    @Override
    protected void onDestroy() {
        Intent data = new Intent();
        data.putExtra("key", list);
        setResult(Activity.RESULT_OK, data);
        saveToDB();
        super.onDestroy();
    }

    // 保存到数据库
    private void saveToDB() {
        for (Reminder r : list) {
            if (r.isSelected()) {
                long times;
                times = task.getStart_at() + r.getDelay() * 60 * 1000;
                task.setAlert_at(times);
            }
        }

        dao.insertTask(task);

    }

    @Override
    public void onClick(View v) {
        if (v == null) {
        } else if (v == saveButton) {
            saveToDB();
            finish();
        }

    }

}
