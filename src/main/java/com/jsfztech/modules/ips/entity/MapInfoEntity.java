package com.jsfztech.modules.ips.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


/**
 * 地图表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public class MapInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//地图目录编号
	@Pattern(regexp = "^\\d{1,11}$",message="地图目录编号必须是数字，且长度在1和11之间")
	private Integer catalogId;
	//地图图片地址
	private String mapName;
	//地图图片地址
	private String mapUrl;
	//地图描述
	private String mapMemo;
	//原点坐标x
	private Double originX;
	//原点坐标y
	private Double originY;
	//区域长度(mm)
	private Double aeraLength;
	//区域宽度(mm)
	private Double aeraWidth;
	//图片高度(px)
	private Double imageHeight;
	//图片宽度(px)
	private Double imageWidth;
	//最大级别
	private Integer zoomMax;
	//最小级别
	private Integer zoomMin;
	//1：正常，0：已删除
	private Integer isdel;

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
	 * 设置：地图目录编号
	 */
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取：地图目录编号
	 */
	public Integer getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置：地图图片地址
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * 获取：地图图片地址
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * 设置：地图图片地址
	 */
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	/**
	 * 获取：地图图片地址
	 */
	public String getMapUrl() {
		return mapUrl;
	}

	public String getMapMemo() {
		return mapMemo;
	}

	public void setMapMemo(String mapMemo) {
		this.mapMemo = mapMemo;
	}

	/**
	 * 设置：原点坐标x
	 */
	public void setOriginX(Double originX) {
		this.originX = originX;
	}
	/**
	 * 获取：原点坐标x
	 */
	public Double getOriginX() {
		return originX;
	}
	/**
	 * 设置：原点坐标y
	 */
	public void setOriginY(Double originY) {
		this.originY = originY;
	}
	/**
	 * 获取：原点坐标y
	 */
	public Double getOriginY() {
		return originY;
	}
	/**
	 * 设置：区域长度(mm)
	 */
	public void setAeraLength(Double aeraLength) {
		this.aeraLength = aeraLength;
	}
	/**
	 * 获取：区域长度(mm)
	 */
	public Double getAeraLength() {
		return aeraLength;
	}
	/**
	 * 设置：区域宽度(mm)
	 */
	public void setAeraWidth(Double aeraWidth) {
		this.aeraWidth = aeraWidth;
	}
	/**
	 * 获取：区域宽度(mm)
	 */
	public Double getAeraWidth() {
		return aeraWidth;
	}
	/**
	 * 设置：图片高度(px)
	 */
	public void setImageHeight(Double imageHeight) {
		this.imageHeight = imageHeight;
	}
	/**
	 * 获取：图片高度(px)
	 */
	public Double getImageHeight() {
		return imageHeight;
	}
	/**
	 * 设置：图片宽度(px)
	 */
	public void setImageWidth(Double imageWidth) {
		this.imageWidth = imageWidth;
	}
	/**
	 * 获取：图片宽度(px)
	 */
	public Double getImageWidth() {
		return imageWidth;
	}
	/**
	 * 设置：最大级别
	 */
	public void setZoomMax(Integer zoomMax) {
		this.zoomMax = zoomMax;
	}
	/**
	 * 获取：最大级别
	 */
	public Integer getZoomMax() {
		return zoomMax;
	}
	/**
	 * 设置：最小级别
	 */
	public void setZoomMin(Integer zoomMin) {
		this.zoomMin = zoomMin;
	}
	/**
	 * 获取：最小级别
	 */
	public Integer getZoomMin() {
		return zoomMin;
	}
	/**
	 * 设置：1：正常，0：已删除
	 */
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	/**
	 * 获取：1：正常，0：已删除
	 */
	public Integer getIsdel() {
		return isdel;
	}

	@Override
	public String toString() {
		return "MapInfoEntity{" +
				"id=" + id +
				", catalogId=" + catalogId +
				", mapName='" + mapName + '\'' +
				", mapUrl='" + mapUrl + '\'' +
				", mapMemo='" + mapMemo + '\'' +
				", originX=" + originX +
				", originY=" + originY +
				", aeraLength=" + aeraLength +
				", aeraWidth=" + aeraWidth +
				", imageHeight=" + imageHeight +
				", imageWidth=" + imageWidth +
				", zoomMax=" + zoomMax +
				", zoomMin=" + zoomMin +
				", isdel=" + isdel +
				'}';
	}
}
