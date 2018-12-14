package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.RailPersonRelEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 电子围栏人员关联表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@Mapper
public interface RailPersonRelDao extends BaseDao<RailPersonRelEntity> {
    int deletePersionId(Object id);
}
