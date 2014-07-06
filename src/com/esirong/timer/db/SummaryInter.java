package com.esirong.timer.db;


/**
 * 概述 将操个数据获统计获取总概述
 * 
 * @author esirong
 * 
 */
public interface SummaryInter {

	/**
	 * 获取全部任务数量
	 * 
	 * @return
	 */
	public int getTotals();

	/**
	 * 获取今天任务的数量
	 * 
	 * @return
	 */
	public int getCountToday();

	/**
	 * 获取已完成的任务数
	 * 
	 * @return
	 */
	public int getCount4Done();

	/**
	 * 获取未完成的任务数
	 * 
	 * @return
	 */
	public int getCount4Undone();

	/**
	 * 获取效率百分比
	 * 
	 * @return
	 */
	public int getEfficientPercent();

	/**
	 * 获取指定类型所占百分比
	 * 
	 * @param taskType
	 * @return
	 */
	public int getPercentType(int taskType);
}
