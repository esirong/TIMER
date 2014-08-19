package com.esirong.timer.model;

/**
 * 序列化它以方便在intent传递
 * @author esirong
 *
 */
public class Reminder {
	/**
	 * 提醒文字
	 */
	private String name;
	/**
	 * 是不是被选中了
	 */
	private boolean selected;
	/**
	 * 类型：-1自定义型，0预选型
	 */
	private int catory = 0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public int getCatory() {
		return catory;
	}
	public void setCatory(int catory) {
		this.catory = catory;
	}
	public Reminder(String name) {
		super();
		this.name = name;
	}
	
	
	
}
