package com.jsfztech.modules.ips.entity;

import java.io.Serializable;


/**
 * 电子围栏人员关联表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public class RailPersonRelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//电子围栏编号
	private Integer railId;
	//人员编号
	private Integer personId;
	//进入围栏报警，1：报警，0：不报警
	private Integer enter;
	//离开围栏报警，1：报警，0：不报警
	private Integer leave;
	private String railName;

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
	 * 设置：电子围栏编号
	 */
	public void setRailId(Integer railId) {
		this.railId = railId;
	}
	/**
	 * 获取：电子围栏编号
	 */
	public Integer getRailId() {
		return railId;
	}
	/**
	 * 设置：人员编号
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	/**
	 * 获取：人员编号
	 */
	public Integer getPersonId() {
		return personId;
	}
	/**
	 * 设置：进入围栏报警，1：报警，0：不报警
	 */
	public void setEnter(Integer enter) {
		this.enter = enter;
	}
	/**
	 * 获取：进入围栏报警，1：报警，0：不报警
	 */
	public Integer getEnter() {
		return enter;
	}
	/**
	 * 设置：离开围栏报警，1：报警，0：不报警
	 */
	public void setLeave(Integer leave) {
		this.leave = leave;
	}
	/**
	 * 获取：离开围栏报警，1：报警，0：不报警
	 */
	public Integer getLeave() {
		return leave;
	}

	public void setRailName(String railName) {
		this.railName = railName;
	}
	public String getRailName() {
		return railName;
	}
}
