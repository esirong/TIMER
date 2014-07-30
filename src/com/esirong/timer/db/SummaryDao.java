package com.esirong.timer.db;

import com.esirong.timer.Task;


/**
 * 概述 将操个数据获统计获取总概述
 * 
 * @author esirong
 * 
 */
public interface SummaryDao {

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

	/**
	 * 获取最近的当前事务：如果有重叠时，按优先级，按加入时间排序
	 */
	public Task getCurrentTask();
}
