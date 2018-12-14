package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.PositionLabelEntity;

import java.util.List;
import java.util.Map;

/**
 * 定位标签表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
public interface PositionLabelService {
	
	PositionLabelEntity queryObject(Integer id);
	
	List<PositionLabelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PositionLabelEntity positionLabel);
	
	void update(PositionLabelEntity positionLabel);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	void updateuse(Map<String, Object> map);
}
