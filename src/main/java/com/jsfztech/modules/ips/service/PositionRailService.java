package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.PositionRailEntity;

import java.util.List;
import java.util.Map;

/**
 * 定位电子围栏表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
public interface PositionRailService {
	
	PositionRailEntity queryObject(Integer id);
	
	List<PositionRailEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PositionRailEntity positionRail);
	
	void update(PositionRailEntity positionRail);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
