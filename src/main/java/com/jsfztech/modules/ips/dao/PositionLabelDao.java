package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.PositionLabelEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定位标签表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
@Mapper
public interface PositionLabelDao extends BaseDao<PositionLabelEntity> {
   void updateuse(Map<String, Object> map);
}
