package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.PositionInfoHistoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-16 14:43:21
 */
public interface PositionInfoHistoryService {
	
	PositionInfoHistoryEntity queryObject(Long id);
	
	List<PositionInfoHistoryEntity> queryList(Map<String, Object> map);

	List<Map<String,Object>> queryExportList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);
	
	void save(PositionInfoHistoryEntity positionInfoHistory);
	
	void update(PositionInfoHistoryEntity positionInfoHistory);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<PositionInfoHistoryEntity> queryByTagId(Map<String, Object> map);
}
