package com.jsfztech.modules.ips.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 人员表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public class PersonsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键，自增长
	private Integer id;
	//姓名
	private String name;
	//0：男，1：女
	private String sex;
	//名族
	private String nation;
	//出生日期
	private String birth;
	//职位
	private String job;
	//工号
	private String employeeNumber;
	//联系方式
	private String phone;
	//家庭住址
	private String address;
	//标签编号
	private String labelId;
	//部门编号
	private Integer deptId;
	//头像地址
	private String photoUrl;
	//0：正常，1：已删除
	private Integer isdel;
	private String deptName;

	private ArrayList<RailPersonRelEntity> rail;

	/**
	 * 设置：主键，自增长
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键，自增长
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：0：男，1：女
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：0：男，1：女
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：名族
	 */
	public void setNation(String nation) {
		this.nation = nation;
	}
	/**
	 * 获取：名族
	 */
	public String getNation() {
		return nation;
	}
	/**
	 * 设置：出生日期
	 */
	public void setBirth(String birth) {
		this.birth = birth;
	}
	/**
	 * 获取：出生日期
	 */
	public String getBirth() {
		return birth;
	}
	/**
	 * 设置：职位
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * 获取：职位
	 */
	public String getJob() {
		return job;
	}
	/**
	 * 设置：工号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * 获取：工号
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * 设置：联系方式
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系方式
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：家庭住址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：家庭住址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：标签编号
	 */
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	/**
	 * 获取：标签编号
	 */
	public String getLabelId() {
		return labelId;
	}
	/**
	 * 设置：部门编号
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门编号
	 */
	public Integer getDeptId() {
		return deptId;
	}
	/**
	 * 设置：头像地址
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * 获取：头像地址
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}
	/**
	 * 设置：0：正常，1：已删除
	 */
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	/**
	 * 获取：0：正常，1：已删除
	 */
	public Integer getIsdel() {
		return isdel;
	}

	public void setRail( ArrayList<RailPersonRelEntity> rail) {
		this.rail = rail;
	}

	public  ArrayList<RailPersonRelEntity> getRail() {
		return rail;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptName() {
		return deptName;
	}


}
