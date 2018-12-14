package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.RailPersonRelEntity;

import java.util.List;
import java.util.Map;

/**
 * 电子围栏人员关联表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public interface RailPersonRelService {
	
	RailPersonRelEntity queryObject(Integer id);
	
	List<RailPersonRelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RailPersonRelEntity railPersonRel);
	
	void update(RailPersonRelEntity railPersonRel);
	
	void delete(Integer id);
	void deletePersionId(Integer persionId);
	
	void deleteBatch(Integer[] ids);
}
