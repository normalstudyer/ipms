package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.MapCatalogEntity;

import java.util.List;
import java.util.Map;

/**
 * 地图目录表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
public interface MapCatalogService {
	
	MapCatalogEntity queryObject(Integer id);

	MapCatalogEntity queryParentObject(Long deptId);

	List<MapCatalogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MapCatalogEntity mapCatalog);
	
	void update(MapCatalogEntity mapCatalog);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	/**
	 * 查询子地图ID列表
	 * @param parentId  上级地图ID
	 */
	List<Integer> queryMapIdList(Integer parentId);
}
