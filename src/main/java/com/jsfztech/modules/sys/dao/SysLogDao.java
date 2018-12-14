package com.jsfztech.modules.sys.dao;

import com.jsfztech.modules.sys.entity.SysLogEntity;
import com.jsfztech.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
    List<Map<String,Object>> queryExportList(Map<String, Object> map);
}
