package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.PersonsEntity;

import java.util.List;
import java.util.Map;

/**
 * 人员表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public interface PersonsService {
	
	PersonsEntity queryObject(Integer id);
	
	List<PersonsEntity> queryList(Map<String, Object> map);
	List<Map<String,Object>> queryExport(Map<String, Object> map);
	int queryTotal(Map<String, Object> map);
	
	void save(PersonsEntity persons);
	
	void update(PersonsEntity persons);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	List<Map<String,Object>> queryPersonTreeList();
}
