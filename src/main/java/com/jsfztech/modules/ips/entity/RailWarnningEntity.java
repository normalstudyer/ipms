package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 电子围栏告警表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public class RailWarnningEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//围栏编号
	private Integer railId;
	//人员编号
	private Integer labelId;
	//触发时间
	private Date triggerDate;
	//1:进入，2：离开
	private Integer triggerType;
	//信息描述
	private String info;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：围栏编号
	 */
	public void setRailId(Integer railId) {
		this.railId = railId;
	}
	/**
	 * 获取：围栏编号
	 */
	public Integer getRailId() {
		return railId;
	}
	/**
	 * 设置：标签编号
	 */
	public void setLabelId(Integer personId) {
		this.labelId = personId;
	}
	/**
	 * 获取：标签编号
	 */
	public Integer getLabelId() {
		return labelId;
	}
	/**
	 * 设置：触发时间
	 */
	public void setTriggerDate(Date triggerDate) {
		this.triggerDate = triggerDate;
	}
	/**
	 * 获取：触发时间
	 */
	public Date getTriggerDate() {
		return triggerDate;
	}
	/**
	 * 设置：1:进入，2：离开
	 */
	public void setTriggerType(Integer triggerType) {
		this.triggerType = triggerType;
	}
	/**
	 * 获取：1:进入，2：离开
	 */
	public Integer getTriggerType() {
		return triggerType;
	}
	/**
	 * 设置：信息描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：信息描述
	 */
	public String getInfo() {
		return info;
	}
}
