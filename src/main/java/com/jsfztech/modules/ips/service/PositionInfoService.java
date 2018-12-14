package com.jsfztech.modules.ips.service;

import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import java.util.List;
import java.util.Map;

/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-07-24 09:12:22
 */
public interface PositionInfoService {
	
	PositionInfoEntity queryObject(Long id);
	
	List<PositionInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	void save(PositionInfoEntity positionInfo,String tablename);
	
	void update(PositionInfoEntity positionInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void queryTable(String tablename);
}
