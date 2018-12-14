package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.PositionInfoHistoryEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-16 14:43:21
 */
@Mapper
public interface PositionInfoHistoryDao extends BaseDao<PositionInfoHistoryEntity> {
    /**
     *tagId, sTime,  eTime
     * @param map
     * @return
     */
    List<PositionInfoHistoryEntity> queryByTagId(Map<String, Object> map);

    List<Map<String,Object>> queryExportList(Map<String, Object> map);
}
