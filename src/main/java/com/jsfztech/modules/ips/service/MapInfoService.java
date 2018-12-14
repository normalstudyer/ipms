package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.MapInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 地图表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public interface MapInfoService {
	
	MapInfoEntity queryObject(Integer id);
	
	List<MapInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MapInfoEntity mapInfo);
	
	void update(MapInfoEntity mapInfo);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
