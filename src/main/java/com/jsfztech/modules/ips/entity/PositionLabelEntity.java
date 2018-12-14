package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 定位标签表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
public class PositionLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//标签编号
	private String tagId;
	//定位目标的名称
	private String targetName;
	//定位目标类型：0：人；1：车；2：物；3：其它
	private String targetType;
	//备注
	private String targetRemarks;
	//是否启用1启用，0关闭
	private String enable;
	//电量
	private String battery;
	//最后更新日期
	private String updateDate;
	//最后位置x
	private String x;
	//最后位置x
	private String y;

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
	 * 设置：标签编号
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * 获取：标签编号
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * 设置：定位目标的名称
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	/**
	 * 获取：定位目标的名称
	 */
	public String getTargetName() {
		return targetName;
	}
	/**
	 * 设置：定位目标类型：0：人；1：车；2：物；3：其它
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	/**
	 * 获取：定位目标类型：0：人；1：车；2：物；3：其它
	 */
	public String getTargetType() {
		return targetType;
	}
	/**
	 * 设置：备注
	 */
	public void setTargetRemarks(String targetRemarks) {
		this.targetRemarks = targetRemarks;
	}
	/**
	 * 获取：备注
	 */
	public String getTargetRemarks() {
		return targetRemarks;
	}
	/**
	 * 设置：是否启用1启用，0关闭
	 */
	public void setEnable(String enable) {
		this.enable = enable;
	}
	/**
	 * 获取：是否启用1启用，0关闭
	 */
	public String getEnable() {
		return enable;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
}
