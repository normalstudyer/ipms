package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 地图目录表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public class MapCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//地图编号
	private Integer id;
	//父级编号
	private Integer pid;
	//地图名称
	private String name;
	//上级地图名称
	private String parentName;
	//目录名称
	private String catalogName;
	//目录说明描述
	private String catalogRemarks;
	//1：正常，0：已删除
	private Integer isdel;
	//排序号
	private Integer orderNum;
	/**
	 * ztree属性
	 */
	private Boolean open;

	private List<?> list;

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
	 * 设置：父级编号
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父级编号
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * 设置：目录名称
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * 获取：目录名称
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置：目录说明描述
	 */
	public void setCatalogRemarks(String catalogRemarks) {
		this.catalogRemarks = catalogRemarks;
	}
	/**
	 * 获取：目录说明描述
	 */
	public String getCatalogRemarks() {
		return catalogRemarks;
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

	public MapCatalogEntity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "MapCatalogEntity{" +
				"id=" + id +
				", pid=" + pid +
				", catalogName='" + catalogName + '\'' +
				", catalogRemarks='" + catalogRemarks + '\'' +
				", isdel=" + isdel +
				'}';
	}

}
