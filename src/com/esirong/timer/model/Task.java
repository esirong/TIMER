package com.esirong.timer.model;

import java.util.Date;

/**
 * ������
 * @author esirong
 *
 */
public class Task {

	/**
	 * ����
	 */
	private String title;
	/**
	 * ����
	 */
	private String note;

	/**
	 * ���ȼ�
	 */
	private int priority;
	/**
	 * ��ʼʱ��
	 */
	private Date startAt;
	/**
	 * ����ʱ��
	 */
	private Date endAt;
	/**
	 * �ǲ��ǽ���
	 */
	private boolean isFinished;
	/**
	 * �ǻ������
	 */
	private boolean isDone;
	/**
	 * ״̬����ʱ����ǰ����
	 */
	private int status;
	/**
	 * ������������
	 */
	private int score;
	/**
	 * ����
	 */
	private int type;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getStartAt() {
		return startAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}