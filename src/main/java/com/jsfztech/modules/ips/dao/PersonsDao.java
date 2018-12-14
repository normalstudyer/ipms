package com.jsfztech.modules.ips.dao;

import com.jsfztech.modules.ips.entity.PersonsEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 人员表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@Mapper
public interface PersonsDao extends BaseDao<PersonsEntity> {
    List<Map<String,Object>> queryPersonTreeList();
    List<Map<String,Object>> queryExport(Map<String, Object> map);
}
