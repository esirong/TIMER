package com.esirong.timer.model;

/**
 * 升级信息
 * @author esirong@qq.com
 *
 */
public class UpdateInfo {
	private String path;
	private int version;
	private String description;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
