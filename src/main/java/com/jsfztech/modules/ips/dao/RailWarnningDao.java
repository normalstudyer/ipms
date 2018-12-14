package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.RailWarnningEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 电子围栏告警表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@Mapper
public interface RailWarnningDao extends BaseDao<RailWarnningEntity> {
	List<Map<String,Object>> queryList1(Map<String, Object> map);

	List<Map<String,Object>> queryExportList(Map<String, Object> map);

	List<Map<String,Object>> queryEcharts();
}
