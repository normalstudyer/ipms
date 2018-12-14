package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.RailWarnningEntity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 电子围栏告警表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public interface RailWarnningService {
	
	RailWarnningEntity queryObject(Integer id);
	
	List<Map<String,Object>> queryList1(Map<String, Object> map);

	/**
	 * 导出数据
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryExportList(Map<String, Object> map) ;

	/**
	 * 获取告警记录表中每个围栏的进出次数
	 * @param
	 * @return
	 */
	List<Map<String,Object>> queryEcharts() ;
	int queryTotal(Map<String, Object> map);
	
	void save(RailWarnningEntity railWarnning);
	
	void update(RailWarnningEntity railWarnning);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
