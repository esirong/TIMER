package com.esirong.timer;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.esirong.timer.Note;
import com.esirong.timer.Task;
import com.esirong.timer.Label;
import com.esirong.timer.Task_Label;
import com.esirong.timer.Task_Goal;
import com.esirong.timer.Goal;
import com.esirong.timer.Address;

import com.esirong.timer.NoteDao;
import com.esirong.timer.TaskDao;
import com.esirong.timer.LabelDao;
import com.esirong.timer.Task_LabelDao;
import com.esirong.timer.Task_GoalDao;
import com.esirong.timer.GoalDao;
import com.esirong.timer.AddressDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig noteDaoConfig;
    private final DaoConfig taskDaoConfig;
    private final DaoConfig labelDaoConfig;
    private final DaoConfig task_LabelDaoConfig;
    private final DaoConfig task_GoalDaoConfig;
    private final DaoConfig goalDaoConfig;
    private final DaoConfig addressDaoConfig;

    private final NoteDao noteDao;
    private final TaskDao taskDao;
    private final LabelDao labelDao;
    private final Task_LabelDao task_LabelDao;
    private final Task_GoalDao task_GoalDao;
    private final GoalDao goalDao;
    private final AddressDao addressDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        noteDaoConfig = daoConfigMap.get(NoteDao.class).clone();
        noteDaoConfig.initIdentityScope(type);

        taskDaoConfig = daoConfigMap.get(TaskDao.class).clone();
        taskDaoConfig.initIdentityScope(type);

        labelDaoConfig = daoConfigMap.get(LabelDao.class).clone();
        labelDaoConfig.initIdentityScope(type);

        task_LabelDaoConfig = daoConfigMap.get(Task_LabelDao.class).clone();
        task_LabelDaoConfig.initIdentityScope(type);

        task_GoalDaoConfig = daoConfigMap.get(Task_GoalDao.class).clone();
        task_GoalDaoConfig.initIdentityScope(type);

        goalDaoConfig = daoConfigMap.get(GoalDao.class).clone();
        goalDaoConfig.initIdentityScope(type);

        addressDaoConfig = daoConfigMap.get(AddressDao.class).clone();
        addressDaoConfig.initIdentityScope(type);

        noteDao = new NoteDao(noteDaoConfig, this);
        taskDao = new TaskDao(taskDaoConfig, this);
        labelDao = new LabelDao(labelDaoConfig, this);
        task_LabelDao = new Task_LabelDao(task_LabelDaoConfig, this);
        task_GoalDao = new Task_GoalDao(task_GoalDaoConfig, this);
        goalDao = new GoalDao(goalDaoConfig, this);
        addressDao = new AddressDao(addressDaoConfig, this);

        registerDao(Note.class, noteDao);
        registerDao(Task.class, taskDao);
        registerDao(Label.class, labelDao);
        registerDao(Task_Label.class, task_LabelDao);
        registerDao(Task_Goal.class, task_GoalDao);
        registerDao(Goal.class, goalDao);
        registerDao(Address.class, addressDao);
    }
    
    public void clear() {
        noteDaoConfig.getIdentityScope().clear();
        taskDaoConfig.getIdentityScope().clear();
        labelDaoConfig.getIdentityScope().clear();
        task_LabelDaoConfig.getIdentityScope().clear();
        task_GoalDaoConfig.getIdentityScope().clear();
        goalDaoConfig.getIdentityScope().clear();
        addressDaoConfig.getIdentityScope().clear();
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public LabelDao getLabelDao() {
        return labelDao;
    }

    public Task_LabelDao getTask_LabelDao() {
        return task_LabelDao;
    }

    public Task_GoalDao getTask_GoalDao() {
        return task_GoalDao;
    }

    public GoalDao getGoalDao() {
        return goalDao;
    }

    public AddressDao getAddressDao() {
        return addressDao;
    }

}
