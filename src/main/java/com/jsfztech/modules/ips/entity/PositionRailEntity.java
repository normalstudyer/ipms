package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 定位电子围栏表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
public class PositionRailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//围栏编号
	private Integer railId;
	//围栏名称
	private String railName;
	//备注
	private String railRemarks;

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
	 * 设置：围栏名称
	 */
	public void setRailName(String railName) {
		this.railName = railName;
	}
	/**
	 * 获取：围栏名称
	 */
	public String getRailName() {
		return railName;
	}
	/**
	 * 设置：备注
	 */
	public void setRailRemarks(String railRemarks) {
		this.railRemarks = railRemarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRailRemarks() {
		return railRemarks;
	}
}
