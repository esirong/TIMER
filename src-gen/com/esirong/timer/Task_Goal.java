package com.esirong.timer;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TASK__GOAL.
 */
public class Task_Goal {

    private Long id;
    private long taskId;
    private long goalId;

    public Task_Goal() {
    }

    public Task_Goal(Long id) {
        this.id = id;
    }

    public Task_Goal(Long id, long taskId, long goalId) {
        this.id = id;
        this.taskId = taskId;
        this.goalId = goalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

}