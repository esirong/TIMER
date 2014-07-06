package com.esirong.timer.db.dao;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * dao�ӿڶ���
 * @author Administrator
 *
 * @param <M>
 */

public interface DAO <M>{
	
	/**
	 * �������
	 * @param m 
	 * @return
	 */
	public long insert(M m);
	
	public int delete(Serializable id);
	
	public int update(M m);
	
	public ArrayList<M> findAll();
	

}