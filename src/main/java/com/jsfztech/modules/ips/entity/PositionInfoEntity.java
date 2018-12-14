package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-07-24 09:12:22
 */
public class PositionInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//数据源名称
	private String sourceName;
	//TP:上传 Tag 位置信息; TD:上传 Tag 自定义数据
	private String formatType;
	//二进制
	private String tagId;
	//二进制
	private String tagIdFormat;
	//坐标x
	private Double x;
	//坐标y
	private Double y;
	//坐标z
	private Double z;
	//电量
	private String battery;
	//推送日期
	private String updateDate;
	//
	private Integer blinkId;
	//
	private Integer qualityIndicator;
	//主基站ID
	private String payload;
	//信息
	private String msg;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：数据源名称
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	/**
	 * 获取：数据源名称
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * 设置：TP:上传 Tag 位置信息; TD:上传 Tag 自定义数据
	 */
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	/**
	 * 获取：TP:上传 Tag 位置信息; TD:上传 Tag 自定义数据
	 */
	public String getFormatType() {
		return formatType;
	}
	/**
	 * 设置：二进制
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * 获取：二进制
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * 设置：二进制
	 */
	public void setTagIdFormat(String tagIdFormat) {
		this.tagIdFormat = tagIdFormat;
	}
	/**
	 * 获取：二进制
	 */
	public String getTagIdFormat() {
		return tagIdFormat;
	}
	/**
	 * 设置：坐标x
	 */
	public void setX(Double x) {
		this.x = x;
	}
	/**
	 * 获取：坐标x
	 */
	public Double getX() {
		return x;
	}
	/**
	 * 设置：坐标y
	 */
	public void setY(Double y) {
		this.y = y;
	}
	/**
	 * 获取：坐标y
	 */
	public Double getY() {
		return y;
	}
	/**
	 * 设置：坐标z
	 */
	public void setZ(Double z) {
		this.z = z;
	}
	/**
	 * 获取：坐标z
	 */
	public Double getZ() {
		return z;
	}
	/**
	 * 设置：电量
	 */
	public void setBattery(String battery) {
		this.battery = battery;
	}
	/**
	 * 获取：电量
	 */
	public String getBattery() {
		return battery;
	}
	/**
	 * 设置：推送日期
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：推送日期
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：
	 */
	public void setBlinkId(Integer blinkId) {
		this.blinkId = blinkId;
	}
	/**
	 * 获取：
	 */
	public Integer getBlinkId() {
		return blinkId;
	}
	/**
	 * 设置：
	 */
	public void setQualityIndicator(Integer qualityIndicator) {
		this.qualityIndicator = qualityIndicator;
	}
	/**
	 * 获取：
	 */
	public Integer getQualityIndicator() {
		return qualityIndicator;
	}
	/**
	 * 设置：主基站ID
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}
	/**
	 * 获取：主基站ID
	 */
	public String getPayload() {
		return payload;
	}
	/**
	 * 设置：信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 获取：信息
	 */
	public String getMsg() {
		return msg;
	}
}
