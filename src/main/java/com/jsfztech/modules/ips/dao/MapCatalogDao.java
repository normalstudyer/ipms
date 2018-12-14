package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.MapCatalogEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地图目录表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@Mapper
public interface MapCatalogDao extends BaseDao<MapCatalogEntity> {
    MapCatalogEntity queryParentObject(Long deptId);
    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Integer> queryMapIdList(Integer parentId);
}
