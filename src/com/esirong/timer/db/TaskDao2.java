package com.esirong.timer.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.esirong.timer.Address;
import com.esirong.timer.AddressDao;
import com.esirong.timer.DaoMaster;
import com.esirong.timer.DaoMaster.DevOpenHelper;
import com.esirong.timer.DaoSession;
import com.esirong.timer.Goal;
import com.esirong.timer.GoalDao;
import com.esirong.timer.Label;
import com.esirong.timer.LabelDao;
import com.esirong.timer.LabelDao.Properties;
import com.esirong.timer.model.TaskInfo;
import com.esirong.timer.Task;
import com.esirong.timer.TaskDao;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.Task_GoalDao;
import com.esirong.timer.Task_Label;
import com.esirong.timer.Task_LabelDao;

import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.QueryBuilder;

public class TaskDao2 {
	private DaoSession daoSession;

	public TaskDao2(Context cxt) {
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(cxt, "AWEEK.db",
				null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
	}

	public void getTask() {
	}

	public void insert(TaskInfo task) {
	}

	public void find() {
	}

	// ��ѯ��������
	public void queryNowTask() {
	}

	// ��ѯ����ִ�е�����
	public void queryRunningTask() {
	}

	// ��������
	public void queryOutDateTask() {
	}

	// ɾ������
	public void queryDeletedTask() {
	}

	// ����ɵ�
	public void queryHasDoneTask() {
	}

	// ��������
	public void queryDelayTask() {
	}

	// δ���
	public void queryNotDone() {
	}

	// ���������
	public void queryTodayTask() {
	}

	// �����
	public void queryTommoryTask() {
	}

	// ��ѯ�����ѵ�
	public void queryRemindTask() {
	}

	// ��ѯ����

	// ������
	public void queryTask() {
	}

	// ������
	public void insertTask() {
	}

	// ��������
	public void updateTask() {
	}

	// ɾ������
	public void deleteTask(Long taskid) {
		TaskDao dao = daoSession.getTaskDao();
		dao.deleteByKey(taskid);
	}

	// ɾ�����е�
	public void deleteAllTask() {
	}

	// ����
	// ��ȡ��������
	public void queryCoverInfo() {

	}

	// ��ȡָ�����͵ĸ�������
	public void queryCoverInfo(int type) {
	}

	// ��ȡָ�������ڼ��
	public void queryCoverInfo(long start, long end) {
	}

	//
	public void queryCoverInfo(int type, long start, long end) {

	}

	public long insertTask(Task task) {
		long id;
		id = daoSession.insertOrReplace(task);
		return id;
	}

	private boolean isSaved(Task task) {
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.TaskDao.Properties.Id.eq(task.getId()),
				com.esirong.timer.TaskDao.Properties.Title.eq(task.getTitle()));

		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	// �����ǩ
	public long insertLabel(Label entity) {
		// 1�����ظ�ֵ
		long id;
		if (isSaved(entity)) {
			return entity.getId();
		} else {
			id = daoSession.insertOrReplace(entity);
			entity.setId(id);
			return id;
		}
	}

	public void insertLabels() {
	}

	// �ǲ����б���ĳ����ǩ
	public boolean isSaved(Label label) {
		LabelDao dao = daoSession.getLabelDao();
		QueryBuilder<Label> qb = dao.queryBuilder();

		qb.where(Properties.Name.eq(label.getName()));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;// �����ղر�
	}

	public ArrayList<Label> findLabelAll() {
		LabelDao dao = daoSession.getLabelDao();
		QueryBuilder<Label> qb = dao.queryBuilder();
		LazyList<Label> list = qb.listLazy();
		ArrayList<Label> labels = new ArrayList<Label>(list);
		return labels;
	}

	public ArrayList<Goal> findGoalAll() {
		GoalDao dao = daoSession.getGoalDao();
		QueryBuilder<Goal> qb = dao.queryBuilder();
		LazyList<Goal> list = qb.listLazy();
		ArrayList<Goal> goals = new ArrayList<Goal>(list);
		return goals;
	}

	public long insertGoal(Goal entity) {
		// 1�����ظ�ֵ
		long id;
		if (isGoalSaved(entity)) {
			return entity.getId();
		} else {
			id = daoSession.insertOrReplace(entity);
			entity.setId(id);
			return id;
		}
	}

	public boolean isGoalSaved(Goal goal) {
		GoalDao dao = daoSession.getGoalDao();
		QueryBuilder<Goal> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.GoalDao.Properties.Name.eq(goal.getName()));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;// �����ղر�
	}

	public Label findLabel(long id) {
		LabelDao dao = daoSession.getLabelDao();
		QueryBuilder<Label> qb = dao.queryBuilder();
		qb.where(Properties.Id.eq(id));
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public Label findLabel(String name) {
		LabelDao dao = daoSession.getLabelDao();
		QueryBuilder<Label> qb = dao.queryBuilder();
		qb.where(Properties.Name.eq(name));
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public Goal findGoal(String name) {
		GoalDao dao = daoSession.getGoalDao();
		QueryBuilder<Goal> qb = dao.queryBuilder();
		qb.where(com.esirong.timer.GoalDao

		.Properties.Name.eq(name));
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public Goal findGoal(long id) {
		GoalDao dao = daoSession.getGoalDao();
		QueryBuilder<Goal> qb = dao.queryBuilder();
		qb.where(com.esirong.timer.GoalDao.Properties.Id.eq(id));
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public long insertTaskLabel(Task_Label entity) {
		if (isTaskLabelSaved(entity)) {
			return findTaskLabel(entity.getTaskId(), entity.getLabelId())
					.getId();
		} else {
			return daoSession.insertOrReplace(entity);
		}
	}

	public boolean isTaskLabelSaved(Task_Label entity) {
		Task_LabelDao dao = daoSession.getTask_LabelDao();
		QueryBuilder<Task_Label> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_LabelDao

		.Properties.TaskId.eq(entity.getTaskId()),
				com.esirong.timer.Task_LabelDao

				.Properties.LabelId.eq(entity.getLabelId()));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	public Task_Label findTaskLabel(long taskId, long labelId) {
		Task_LabelDao dao = daoSession.getTask_LabelDao();
		QueryBuilder<Task_Label> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_LabelDao

		.Properties.TaskId.eq(taskId), com.esirong.timer.Task_LabelDao

		.Properties.LabelId.eq(labelId));
		qb.buildCount().count();
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public boolean isTaskGoalSaved(Task_Goal entity) {
		Task_GoalDao dao = daoSession.getTask_GoalDao();
		QueryBuilder<Task_Goal> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_GoalDao.Properties.TaskId.eq(entity
				.getTaskId()), com.esirong.timer.Task_GoalDao.Properties.GoalId
				.eq(entity.getGoalId()));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;
	}

	public Task_Goal findTaskGoal(long taskId, long goalId) {
		Task_GoalDao dao = daoSession.getTask_GoalDao();
		QueryBuilder<Task_Goal> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_GoalDao.Properties.TaskId.eq(taskId),
				com.esirong.timer.Task_GoalDao.Properties.GoalId.eq(goalId));
		qb.buildCount().count();
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}

	public long insertTaskGoal(Task_Goal entity) {
		if (isTaskGoalSaved(entity)) {
			return findTaskGoal(entity.getTaskId(), entity.getGoalId())
					.getId();
		} else {
			return daoSession.insertOrReplace(entity);
		}
	}

	public List<Task> findTaskAll() {
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		LazyList<Task> list = qb.listLazy();
		ArrayList<Task> tasks = new ArrayList<Task>(list);
		return tasks;

	}
	public List<Task> findTaskAll(String type) {
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		qb.where( com.esirong.timer.TaskDao
				.Properties.Type.eq(type));
		LazyList<Task> list = qb.listLazy();
		ArrayList<Task> tasks = new ArrayList<Task>(list);
		return tasks;

	}
	public ArrayList<Address> findAddressAll() {
		AddressDao dao = daoSession.getAddressDao();
		QueryBuilder<Address> qb = dao.queryBuilder();
		LazyList<Address> list = qb.listLazy();
		ArrayList<Address> addresses = new ArrayList<Address>(list);
		return addresses;
	}

	public boolean isAddressSaved(Address entity) {

		AddressDao dao = daoSession.getAddressDao();
		QueryBuilder<Address> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.AddressDao
		.Properties.Name.eq(entity.getName()));
		qb.buildCount().count();
		return qb.buildCount().count() > 0 ? true : false;// �����ղر�
	}
	
	public long insertAddress(Address entity) {
		if (isAddressSaved(entity)) {
			return findAddress(entity.getName())
					.getId();
		} else {
			return daoSession.insertOrReplace(entity);
		}
	}
	
	public Address findAddress(String name) {
		AddressDao dao = daoSession.getAddressDao();
		QueryBuilder<Address> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.AddressDao
		.Properties.Name.eq(name));
		qb.buildCount().count();
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}

	}
	
	public long findUnDonedTask(){
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		qb.where(com.esirong.timer.TaskDao
		.Properties.Done.eq(false));
		return qb.count();
	}
	public long findCountTask(){
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		return qb.count();
	}
	public long findDonedTask(){
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		qb.where(com.esirong.timer.TaskDao
				.Properties.Done.eq(true));
		return qb.count();
	}
	
	//总共用时间
	public long findComsumerTime(){
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();
		long comsumer = 0;
		for(Task item:qb.build().list()){
			comsumer +=item.getEnd_at()-item.getStart_at();
		}
		return comsumer;
		
	}

	public void deleteTaskGoal(Task_Goal entity) {
		if (isTaskGoalSaved(entity)) {
			daoSession.delete(entity);
		}
		
	}

	public void deleteTaskLabel(Task_Label entity) {
		if (isTaskLabelSaved(entity)) {
			daoSession.delete(entity);
		}
	}

	public Task findTask(Long taskId) {
		TaskDao dao = daoSession.getTaskDao();
		QueryBuilder<Task> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.TaskDao.Properties.Id.eq(taskId));
		qb.buildCount().count();
		if (qb.list().size() > 0) {
			return qb.list().get(0);
		} else {
			return null;
		}
	}

	public List<Task_Goal> findGoalByTaskId(Long taskId) {
		Task_GoalDao dao = daoSession.getTask_GoalDao();
		QueryBuilder<Task_Goal> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_GoalDao.Properties.TaskId.eq(taskId));
		qb.buildCount().count();
		return qb.list();
		
	}

	public List<Task_Label> findLabelByTaskId(Long taskId) {
		Task_LabelDao dao = daoSession.getTask_LabelDao();
		QueryBuilder<Task_Label> qb = dao.queryBuilder();

		qb.where(com.esirong.timer.Task_LabelDao.Properties.TaskId.eq(taskId));
		qb.buildCount().count();
		return qb.list();
	}


}
