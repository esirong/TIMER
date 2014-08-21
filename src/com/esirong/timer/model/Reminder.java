package com.esirong.timer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 序列化它以方便在intent传递
 * 
 * @author esirong
 * 
 */
public class Reminder implements Parcelable {
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
	private long delay = 0;

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

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

	public Reminder(String name,long delay) {
		super();
		this.name = name;
		this.delay = delay;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
		public Reminder createFromParcel(Parcel in) {
			return new Reminder(in);
		}

		public Reminder[] newArray(int size) {
			return new Reminder[size];
		}
	};

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	private Reminder(Parcel in) {
		name = in.readString();
	}
}
