package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-07-24 09:12:22
 */
@Mapper
public interface PositionInfoDao extends BaseDao<PositionInfoEntity> {
    void queryTable(@Param("tablename") String tablename);

    void save(@Param("positionInfo") PositionInfoEntity positionInfo,@Param("tablename") String tablename);
}
